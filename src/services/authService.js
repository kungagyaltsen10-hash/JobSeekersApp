import api from './api';

const LOCAL_TOKEN_PREFIX = 'local-dev-token-';

const pickToken = (data = {}) => data.token || data.accessToken || data.jwt || data?.data?.token || data?.data?.accessToken;

const pickUser = (data = {}, fallbackPayload = {}) =>
  data.user ||
  data.profile ||
  data?.data?.user || {
    name: fallbackPayload.name || fallbackPayload.email?.split('@')[0] || 'User',
    email: fallbackPayload.email || '',
  };

const normalizeAuthPayload = (data, fallbackPayload) => {
  const token = pickToken(data);
  const user = pickUser(data, fallbackPayload);

  if (!token) {
    throw new Error('Missing token in authentication response.');
  }

  return { token, user };
};

const createLocalSession = (payload = {}) => ({
  token: `${LOCAL_TOKEN_PREFIX}${Date.now()}`,
  user: {
    name: payload.name || payload.email?.split('@')[0] || 'Local User',
    email: payload.email || 'local@example.com',
  },
  isLocalFallback: true,
});

export const authService = {
  signup: async (payload) => {
    try {
      const { data } = await api.post('/auth/signup', payload);
      return normalizeAuthPayload(data, payload);
    } catch (error) {
      if (!error.response) {
        return createLocalSession(payload);
      }
      throw error;
    }
  },
  login: async (payload) => {
    try {
      const { data } = await api.post('/auth/login', payload);
      return normalizeAuthPayload(data, payload);
    } catch (error) {
      if (!error.response) {
        return createLocalSession(payload);
      }
      throw error;
    }
  },
  me: async () => {
    const { data } = await api.get('/auth/me');
    return data.user || data?.data?.user || data;
  },
  isLocalToken: (token = '') => token.startsWith(LOCAL_TOKEN_PREFIX),
};
