import React from 'react'

const Footer = () => {
    return (
        <div>
            <footer className='footer bg-dark text-white text-center py-2 mt-auto'>
                <span>Anthony Fernandez &copy; {new Date().getFullYear()}</span>
            </footer>
        </div>
    )
}

export default Footer