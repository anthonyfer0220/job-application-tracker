import React from 'react'
import { Link, useNavigate } from 'react-router-dom'

const Header = () => {

    const navigate = useNavigate();

    const handleLogout = () => {
        console.log("Logout");
    }

    return (
        <header className='header'>
            <nav className='navbar navbar-expand-lg px-4'>
                <Link to='/dashboard' className='navbar-brand fw-bold'>
                    Job Application Tracker
                </Link>

                <ul className='navbar-nav ms-auto'>
                    <li className='nav-item'>
                        <Link to='/dashboard' className='nav-link'>
                            Dashboard
                        </Link>
                    </li>
                    <li className='nav-item'>
                        <button className='btn btn-outline-light ms-2' onClick={handleLogout}>
                            Logout
                        </button>
                    </li>
                </ul>

            </nav>
        </header>
    )
}

export default Header