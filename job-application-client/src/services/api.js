import axios from 'axios';

const api = axios.create({
  baseURL: 'http://JobApp-APIGa-ksS1m2ICNDcd-2027852023.us-east-2.elb.amazonaws.com',
  headers: {
    'Content-Type': 'application/json'
  }
});

api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) config.headers.Authorization = `Bearer ${token}`;
  return config;
});

api.interceptors.response.use(
  (resp) => resp,
  (err) => {
    if (err?.response?.status === 401) {
      localStorage.removeItem('token');
    }

    return Promise.reject(err);
  }
);

export default api