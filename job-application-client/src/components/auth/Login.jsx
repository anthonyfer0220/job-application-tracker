import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';

function Login() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const navigate = useNavigate();

  const handleSubmit = (e) => {
    e.preventDefault();

    console.log('Login submitted: ', { email, password });

    navigate('/dashboard');
  };

  return (
    <div className='auth-container'>
      <div className='auth-card'>
        <h2>Login</h2>
        <p className='auth-subtitle'>Welcome back! Please enter your details</p>
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

          <button className='btn btn-brand' type='submit'>Login</button>
          <p className='auth-actions'>
            Don't have an account? <Link to='/signup' className='auth-link'>Sign Up</Link>
          </p>
        </form>
      </div>
    </div>
  );
}

export default Login;