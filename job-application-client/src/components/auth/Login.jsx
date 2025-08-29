import React, { useState } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import AuthService from '../../services/AuthService';

function Login() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [submitting, setSubmitting] = useState(false);
  const [error, setError] = useState('');

  const navigate = useNavigate();
  const location = useLocation();

  // If a protected route sent the user here, ho back there after login; else fo to /dashboard
  const from = location.state?.from?.pathname || '/dashboard';

  const handleSubmit = async (e) => {
    e.preventDefault();
    setSubmitting(true);

    try {
      const payload = { email: email.trim().toLowerCase(), password };
      const resp = await AuthService.loginUser(payload);
      navigate(from, { replace: true });
    } catch (err) {
      setError(err?.response?.data?.message || 'Invalid email or password. Please try again');
    } finally {
      setSubmitting(false);
    }

  };

  return (
    <div className='auth-container'>
      <div className='auth-card'>
        <h2>Login</h2>
        <p className='auth-subtitle'>Welcome back! Please enter your details</p>

        {error && <div className='alert alert-soft-danger mb-3'>{error}</div>}

        <form onSubmit={handleSubmit}>

          <div className='form-group'>
            <label>Email: </label>
            <input
              type='email'
              autoComplete='email'
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
          </div>
          <div className='form-group'>
            <label>Password: </label>
            <input
              type='password'
              autoComplete='current-password'
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>

          <button className='btn btn-brand' type='submit' disabled={submitting}>
            {submitting ? 'Logging in...' : 'Login'}
          </button>

          <p className='auth-actions'>
            Don't have an account? <Link to='/signup' className='auth-link'>Sign Up</Link>
          </p>
        </form>
      </div>
    </div>
  );
}

export default Login;