import './App.css'
import Header from './components/layout/Header'
import Footer from './components/layout/Footer'
import Landing from './components/landing/Landing'
import Signup from './components/auth/Signup'
import Login from './components/auth/Login'
import ListJobApplications from './components/jobs/ListJobApplications'
import JobApplication from './components/jobs/JobApplication'
import { BrowserRouter, Route, Routes, useLocation, Navigate } from 'react-router-dom'

function AppContent() {
  const { pathname } = useLocation();
  const hideHeader = ['/', '/signup', '/login'].includes(pathname)

  return (
    <div className='page-container'>
      {!hideHeader && <Header />}
      <div className='content-wrap'>
        <Routes>
          <Route path="/" element={<Landing />} />
          <Route path="/signup" element={<Signup />} />
          <Route path="/login" element={<Login />} />

          <Route path="/dashboard" element={<ListJobApplications />} />
          <Route path="/dashboard/add" element={<JobApplication />} />
          <Route path="/dashboard/edit/:id" element={<JobApplication />} />

          <Route path="*" element={<Navigate to='/' replace />} />
        </Routes>
      </div>
      <Footer />
    </div>
  )
}

function App() {
  return (
    <BrowserRouter>
      <AppContent />
    </BrowserRouter>
  )
}

export default App