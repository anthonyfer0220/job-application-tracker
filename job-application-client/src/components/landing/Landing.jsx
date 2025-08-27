import React from 'react'
import { Link } from 'react-router-dom'

const Landing = () => {
  return (
    <div className='landing-page d-flex align-items-center justify-content-center'>
      <div className='text-center text-white'>
        <h1 className='display-4 mb-3'>Welcome to Job Application Tracker</h1>
        <p className='lead mb-4'>
          Keep track of all your job applications in one place.
          Sign up or log in to get started.
        </p>
        <div>
          <Link to='/signup' className='btn btn-primary btn-large me-2'>
            Sign Up
          </Link>
          <Link to='/login' className='btn btn-outline-light btn-large'>
            Log In
          </Link>
        </div>

      </div>

    </div>
  )
}

export default Landing