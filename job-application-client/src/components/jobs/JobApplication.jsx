import React, { useState, useEffect } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import { createJobApplication, getJobApplication, updateJobApplication } from '../../services/JobApplicationService'

const JobApplication = () => {

  const today = () => new Date().toISOString().slice(0, 10);

  const [companyName, setCompanyName] = useState('')
  const [position, setPosition] = useState('')
  const [dateApplied, setDateApplied] = useState(today())
  const [oaDate, setOaDate] = useState('')
  const [latestInterviewDate, setLatestInterviewDate] = useState('')
  const [finalDecision, setFinalDecision] = useState('PENDING')

  const { id } = useParams();

  const [errors, setErrors] = useState({
    companyName: '',
    position: '',
  })

  const navigate = useNavigate();

  useEffect(() => {
    if (id) {
      getJobApplication(id).then((response) => {
        setCompanyName(response.data.companyName ?? '');
        setPosition(response.data.position ?? '');
        setDateApplied(response.data.dateApplied ?? '');
        setOaDate(response.data.oaDate ?? '');
        setLatestInterviewDate(response.data.latestInterviewDate ?? '');
        setFinalDecision(response.data.finalDecision ?? '');
      }).catch(error => {
        console.error(error);
      })
    }
  }, [id])

  function validateForm() {
    let valid = true;

    const errorsCopy = { ...errors }

    if (companyName.trim()) {
      errorsCopy.companyName = '';
    } else {
      errorsCopy.companyName = 'Company Name is required';
      valid = false;
    }

    if (position.trim()) {
      errorsCopy.position = '';
    } else {
      errorsCopy.position = 'Position is required';
      valid = false;
    }

    setErrors(errorsCopy);

    return valid;
  }

  function handleSubmit(e) {
    e.preventDefault();

    if (!validateForm()) return;

    const jobApplication = { companyName, position, dateApplied, oaDate, latestInterviewDate, finalDecision };

    const action = id
      ? updateJobApplication(id, jobApplication)
      : createJobApplication(jobApplication);

    action
      .then(() => navigate('/dashboard'))
      .catch(console.error);
  }

  const title = id ? 'Update Job Application' : 'Add Job Application';

  return (
    <div className='dashboard-page'>
      <div className='form-shell'>
        <div className='dashboard-card p-4'>
          <h2 className='h4 text-center'>{title}</h2>
          <hr className='mb-4' />

          <form onSubmit={handleSubmit}>
            <div className='form-group mb-2'>
              <label className='form-label'>Company Name:</label>
              <input
                type='text'
                placeholder='Enter Company Name'
                name='companyName'
                value={companyName}
                className={`form-control ${errors.companyName ? 'is-invalid' : ''}`}
                onChange={(e) => setCompanyName(e.target.value)}
              >
              </input>
              {errors.companyName && <div className='invalid-feedback'> {errors.companyName} </div>}
            </div>
            <div className='form-group mb-2'>
              <label className='form-label'>Position:</label>
              <input
                type='text'
                placeholder='Enter Position'
                name='position'
                value={position}
                className={`form-control ${errors.position ? 'is-invalid' : ''}`}
                onChange={(e) => setPosition(e.target.value)}
              >
              </input>
              {errors.position && <div className='invalid-feedback'> {errors.position} </div>}
            </div>
            <div className='form-group mb-2'>
              <label className='form-label'>Date Applied:</label>
              <input
                type='date'
                name='dateApplied'
                value={dateApplied}
                className='form-control'
                onChange={(e) => setDateApplied(e.target.value)}
              >
              </input>
            </div>
            <div className='form-group mb-2'>
              <label className='form-label'>OA Date:</label>
              <input
                type='date'
                name='oaDate'
                value={oaDate}
                className='form-control'
                onChange={(e) => setOaDate(e.target.value)}
              >
              </input>
            </div>
            <div className='form-group mb-2'>
              <label className='form-label'>Latest Interview Date:</label>
              <input
                type='date'
                name='latestInterviewDate'
                value={latestInterviewDate}
                className='form-control'
                onChange={(e) => setLatestInterviewDate(e.target.value)}
              >
              </input>
            </div>
            <div className='form-group mb-3'>
              <label className='form-label'>Final Decision:</label>
              <select
                name='finalDecision'
                value={finalDecision}
                className='form-select'
                onChange={(e) => setFinalDecision(e.target.value)}
              >
                <option value='OFFERED'>OFFERED</option>
                <option value='REJECTED'>REJECTED</option>
                <option value='PENDING'>PENDING</option>
              </select>
            </div>

            <button type='submit' className='btn btn-brand w-100'>Submit</button>
          </form>
        </div>
      </div>
    </div>
  )
}

export default JobApplication