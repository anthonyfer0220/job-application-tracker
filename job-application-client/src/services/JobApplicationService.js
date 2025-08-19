import axios from 'axios';

const BASE_URL = "http://localhost:4005/api/job-applications";

export const listJobApplications = () => axios.get(BASE_URL);

export const createJobApplication = (jobApplication) => axios.post(BASE_URL, jobApplication);

export const updateJobApplication = (jobApplicationId, jobApplication) => axios.put(`${BASE_URL}/${jobApplicationId}`, jobApplication);

export const getJobApplication = (jobApplicationId) => axios.get(`${BASE_URL}/${jobApplicationId}`);

export const deleteJobApplication = (jobApplicationId) => axios.delete(`${BASE_URL}/${jobApplicationId}`);