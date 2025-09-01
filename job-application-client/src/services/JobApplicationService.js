import api from './api';

const BASE_URL = '/api/job-applications';

export const listJobApplications = (params) => api.get(BASE_URL, { params });

export const createJobApplication = (jobApplication) => api.post(BASE_URL, jobApplication);

export const updateJobApplication = (jobApplicationId, jobApplication) => api.put(`${BASE_URL}/${jobApplicationId}`, jobApplication);

export const getJobApplication = (jobApplicationId) => api.get(`${BASE_URL}/${jobApplicationId}`);

export const deleteJobApplication = (jobApplicationId) => api.delete(`${BASE_URL}/${jobApplicationId}`);