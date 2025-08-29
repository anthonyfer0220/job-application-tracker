import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import AuthService from '../../services/AuthService';

function Signup() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [submitting, setSubmitting] = useState(false);
  const [success, setSuccess] = useState('');
  const [error, setError] = useState('');

  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (password !== confirmPassword) {
      setError('Passwords do not match. Please try again');
      setTimeout(() => setError(''), 3000);
      return;
    }

    try {
      setSubmitting(true);
      const payload = {
        email: email.trim().toLowerCase(),
        password
      };

      await AuthService.registerUser(payload);

      setSuccess('Account created successfully! Redirecting to login...');

      setTimeout(() => {
        navigate('/login', { replace: true });
      }, 3000);

    } catch (err) {
      const message =
        err?.response?.data?.message ||
        (err?.response?.status === 409
          ? 'Email already registered'
          : 'Unable to create account. Please try again'
        );
      setError(message);
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div className='auth-container'>
      <div className='auth-card'>
        <h2>Sign Up</h2>
        <p className='auth-subtitle'>Create your account to get started</p>

        {success && <div className='alert alert-soft-success mb-3'>{success}</div>}
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
              autoComplete='new-password'
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>
          <div className='form-group'>
            <label>Confirm Password: </label>
            <input
              type='password'
              autoComplete='confirm-new-password'
              value={confirmPassword}
              onChange={(e) => setConfirmPassword(e.target.value)}
              required
            />
          </div>

          <button className='btn btn-brand' type='submit' disabled={submitting}>
            {submitting ? 'Creating account...' : 'Sign Up'}
          </button>

          <p className='auth-actions'>
            Already have an account? <Link to='/login' className='auth-link'>Login</Link>
          </p>
        </form>
      </div>
    </div>
  );
}

export default Signup;