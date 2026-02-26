import api from './api';

export const profileService = {
  uploadResume: async (file) => {
    const formData = new FormData();
    formData.append('resume', file);

    const { data } = await api.post('/profile/resume', formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });

    return data;
  },
  saveLocationPreferences: async (payload) => {
    const { data } = await api.put('/profile/location-preferences', payload);
    return data;
  },
};
