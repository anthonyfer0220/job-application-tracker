import api from './api'

class AuthService {
  static async registerUser(payload) {
    return api.post('/auth/signup', payload);
  }

  static async loginUser(payload) {
    const resp = await api.post('/auth/login', payload);
    const token = resp?.data?.token;

    if (token) localStorage.setItem('token', token);
    return resp;
  }

  static async validateToken() {
    return api.get('/auth/validate');
  }

  static logout() {
    localStorage.removeItem('token');
  }

  static async isAuthenticated() {
    return !!localStorage.getItem('token');
  }

  static async getToken() {
    return localStorage.getItem('token');
  }
}

export default AuthService;