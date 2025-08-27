import React, { useState } from 'react';
import { Link } from 'react-router-dom';

function Signup() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();

    if (password !== confirmPassword) {
      alert('Passwords do not match');
      return;
    }

    console.log('Signup submitted: ', { email, password });
  };

  return (
    <div className='auth-container'>
      <div className='auth-card'>
        <h2>Sign Up</h2>
        <p className='auth-subtitle'>Create your account to get started</p>
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

          <button className='btn btn-brand' type='submit'>Sign Up</button>
          <p className='auth-actions'>
            Already have an account? <Link to='/login' className='auth-link'>Login</Link>
          </p>
        </form>
      </div>
    </div>
  );
}

export default Signup;