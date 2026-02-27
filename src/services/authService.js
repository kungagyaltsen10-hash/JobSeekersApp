import api from './api';

const LOCAL_TOKEN_PREFIX = 'local-dev-token-';

const LOGIN_ENDPOINTS = ['/auth/login', '/auth/signin', '/login'];
const SIGNUP_ENDPOINTS = ['/auth/signup', '/auth/register', '/signup', '/register'];

const pickToken = (data = {}) => data.token || data.accessToken || data.jwt || data?.data?.token || data?.data?.accessToken;

const pickUser = (data = {}, fallbackPayload = {}) =>
  data.user ||
  data.profile ||
  data?.data?.user || {
    name: fallbackPayload.name || fallbackPayload.fullName || fallbackPayload.email?.split('@')[0] || 'User',
    email: fallbackPayload.email || '',
  };

const normalizeAuthPayload = (data, fallbackPayload) => {
  const token = pickToken(data);
  const user = pickUser(data, fallbackPayload);
  return { token, user, requiresLogin: !token };
};

const createLocalSession = (payload = {}) => ({
  token: `${LOCAL_TOKEN_PREFIX}${Date.now()}`,
  user: {
    name: payload.name || payload.fullName || payload.email?.split('@')[0] || 'Local User',
    email: payload.email || 'local@example.com',
  },
  isLocalFallback: true,
  requiresLogin: false,
});

const buildAuthPayload = (payload = {}) => ({
  ...payload,
  fullName: payload.fullName || payload.name,
  username: payload.username || payload.email,
});

const extractErrorMessage = (error) =>
  error?.response?.data?.message || error?.response?.data?.error || error?.message || 'Authentication request failed.';

const isRetryableEndpointError = (error) => {
  if (!error?.response) return false;
  const { status } = error.response;
  return status === 404 || status === 405;
};

const isNetworkAxiosError = (error) => Boolean(error?.isAxiosError && !error?.response);

const buildEndpointNotFoundError = (flowName, endpoints) => {
  const apiBase = import.meta.env.VITE_API_BASE_URL;
  return new Error(
    `${flowName} endpoint not found (404/405). Checked: ${endpoints.join(', ')}. ` +
      `Please verify VITE_API_BASE_URL is correct (current: ${apiBase || 'not set'}) and backend auth routes are available.`
  );
};

const requestAcrossEndpoints = async (endpoints, payload, flowName) => {
  let lastError;
  let sawOnlyNotFoundOrMethodErrors = false;

  for (const endpoint of endpoints) {
    try {
      const { data } = await api.post(endpoint, payload);
      return { data, endpoint };
    } catch (error) {
      lastError = error;
      if (isRetryableEndpointError(error)) {
        sawOnlyNotFoundOrMethodErrors = true;
        continue;
      }
      throw error;
    }
  }

  if (sawOnlyNotFoundOrMethodErrors) {
    throw buildEndpointNotFoundError(flowName, endpoints);
  }

  throw lastError;
};

export const authService = {
  signup: async (payload) => {
    const requestPayload = buildAuthPayload(payload);
    try {
      const { data } = await requestAcrossEndpoints(SIGNUP_ENDPOINTS, requestPayload, 'Signup');
      const normalized = normalizeAuthPayload(data, requestPayload);

      if (!normalized.requiresLogin) {
        return normalized;
      }

      const loginResult = await requestAcrossEndpoints(
        LOGIN_ENDPOINTS,
        {
          email: requestPayload.email,
          password: requestPayload.password,
        },
        'Login'
      );

      return normalizeAuthPayload(loginResult.data, requestPayload);
    } catch (error) {
      if (isNetworkAxiosError(error)) {
        return createLocalSession(requestPayload);
      }
      throw new Error(extractErrorMessage(error));
    }
  },

  login: async (payload) => {
    const requestPayload = buildAuthPayload(payload);
    try {
      const { data } = await requestAcrossEndpoints(LOGIN_ENDPOINTS, requestPayload, 'Login');
      const normalized = normalizeAuthPayload(data, requestPayload);

      if (normalized.requiresLogin) {
        throw new Error('Login response did not include a token.');
      }

      return normalized;
    } catch (error) {
      if (isNetworkAxiosError(error)) {
        return createLocalSession(requestPayload);
      }
      throw new Error(extractErrorMessage(error));
    }
  },

  me: async () => {
    const { data } = await api.get('/auth/me');
    return data.user || data?.data?.user || data;
  },

  isLocalToken: (token = '') => token.startsWith(LOCAL_TOKEN_PREFIX),
};
