import './App.css'
import Header from './components/layout/Header'
import Footer from './components/layout/Footer'
import Landing from './components/landing/Landing'
import Signup from './components/auth/Signup'
import Login from './components/auth/Login'
import ListJobApplications from './components/jobs/ListJobApplications'
import JobApplication from './components/jobs/JobApplication'
import { BrowserRouter, Route, Routes, useLocation, Navigate } from 'react-router-dom'

function ProtectedRoute({ element }) {
  const token = localStorage.getItem('token');
  const location = useLocation();

  return token
    ? element
    : <Navigate to='login' replace state={{ from: location }} />;
}

function AppContent() {
  const { pathname } = useLocation();
  const hideHeader = ['/', '/signup', '/login'].includes(pathname)

  return (
    <div className='page-container'>
      {!hideHeader && <Header />}
      <div className='content-wrap'>
        <Routes>
          {/* Public Route */}
          <Route path="/" element={<Landing />} />
          <Route path="/signup" element={<Signup />} />
          <Route path="/login" element={<Login />} />

          {/* Protected Route */}
          <Route path="/dashboard" element={<ProtectedRoute element={<ListJobApplications />} />} />
          <Route path="/dashboard/add" element={<ProtectedRoute element={<JobApplication />} />} />
          <Route path="/dashboard/edit/:id" element={<ProtectedRoute element={<JobApplication />} />} />

          {/* Fallback Route */}
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