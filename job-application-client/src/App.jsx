import './App.css'
import Header from './components/Header'
import Footer from './components/Footer'
import ListJobApplications from './components/ListJobApplications'
import JobApplication from './components/JobApplication'
import { BrowserRouter, Route, Routes } from 'react-router-dom'

function App() {

  return (
    <BrowserRouter>
      <div className='page-container'>
        <Header />
        <div className='content-wrap'>
          <Routes>
            <Route path="/" element={<ListJobApplications />} />
            <Route path="/dashboard" element={<ListJobApplications />} />
            <Route path="/dashboard/add" element={<JobApplication />} />
            <Route path="/dashboard/edit/:id" element={<JobApplication />} />
          </Routes>
        </div>
        <Footer />
      </div>
    </BrowserRouter>
  )
}

export default App