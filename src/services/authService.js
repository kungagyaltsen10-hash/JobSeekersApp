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

const requestAcrossEndpoints = async (endpoints, payload) => {
  let lastError;

  for (const endpoint of endpoints) {
    try {
      const { data } = await api.post(endpoint, payload);
      return { data, endpoint };
    } catch (error) {
      lastError = error;
      if (!isRetryableEndpointError(error)) {
        throw error;
      }
    }
  }

  throw lastError;
};

export const authService = {
  signup: async (payload) => {
    const requestPayload = buildAuthPayload(payload);
    try {
      const { data } = await requestAcrossEndpoints(SIGNUP_ENDPOINTS, requestPayload);
      const normalized = normalizeAuthPayload(data, requestPayload);

      if (!normalized.requiresLogin) {
        return normalized;
      }

      const loginResult = await requestAcrossEndpoints(LOGIN_ENDPOINTS, {
        email: requestPayload.email,
        password: requestPayload.password,
      });

      return normalizeAuthPayload(loginResult.data, requestPayload);
    } catch (error) {
      if (!error.response) {
        return createLocalSession(requestPayload);
      }
      throw new Error(extractErrorMessage(error));
    }
  },

  login: async (payload) => {
    const requestPayload = buildAuthPayload(payload);
    try {
      const { data } = await requestAcrossEndpoints(LOGIN_ENDPOINTS, requestPayload);
      const normalized = normalizeAuthPayload(data, requestPayload);

      if (normalized.requiresLogin) {
        throw new Error('Login response did not include a token.');
      }

      return normalized;
    } catch (error) {
      if (!error.response) {
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
