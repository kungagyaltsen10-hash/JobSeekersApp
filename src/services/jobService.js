import api from './api';

export const jobService = {
  getMatches: async () => {
    const { data } = await api.get('/jobs/matches');
    return data;
  },
  getApplications: async () => {
    const { data } = await api.get('/jobs/applications');
    return data;
  },
};
