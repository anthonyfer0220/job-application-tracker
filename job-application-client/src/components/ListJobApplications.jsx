import React, { useState, useEffect } from 'react'
import { useNavigate } from 'react-router-dom'
import { listJobApplications, deleteJobApplication as deleteJobApplicationFromServer } from '../services/JobApplicationService'

function formatDate(dateStr) {
    if (!dateStr) return '';
    const [year, month, day] = dateStr.split('-');
    return `${month}/${day}/${year}`;
}

const ListJobApplications = () => {

    const [jobApplications, setJobApplications] = useState([])

    const navigate = useNavigate();

    useEffect(() => {
        getAllJobApplications();
    }, [])

    function getAllJobApplications() {
        listJobApplications().then((response) => {
            setJobApplications(response.data);
        }).catch(error => {
            console.error(error);
        })
    }

    function addNewJobApplication() {
        navigate('/dashboard/add')
    }

    function updateJobApplication(id) {
        navigate(`/dashboard/edit/${id}`)
    }

    function deleteJobApplication(id) {
        deleteJobApplicationFromServer(id).then((response) => {
            getAllJobApplications();
        }).catch(error => {
            console.error(error);
        })
    }

    return (
        <div className='dashboard-page'>
            <div className='container'>
                <div className='dashboard-header'>
                    <h2 className='h3'>My Job Applications</h2>
                    <div className='d-flex justify-content-center mb-3'>
                        <button className='btn btn-brand' onClick={addNewJobApplication}>New Entry</button>
                    </div>
                </div>
                <div className='dashboard-table'>
                    <div className='table-responsive'>
                        <table className='table table-hover align-middle'>
                            <thead className='table-light'>
                                <tr>
                                    <th>Company Name</th>
                                    <th>Position</th>
                                    <th>Date Applied</th>
                                    <th>OA Date</th>
                                    <th>Latest Interview Date</th>
                                    <th>Final Decision</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                {
                                    jobApplications.map(jobApplication =>
                                        <tr key={jobApplication.id}>
                                            <td>{jobApplication.companyName}</td>
                                            <td>{jobApplication.position}</td>
                                            <td>{formatDate(jobApplication.dateApplied)}</td>
                                            <td>{formatDate(jobApplication.oaDate)}</td>
                                            <td>{formatDate(jobApplication.latestInterviewDate)}</td>
                                            <td>{jobApplication.finalDecision}</td>
                                            <td>
                                                <button className='btn btn-outline-primary btn-sm' onClick={() =>
                                                    updateJobApplication(jobApplication.id)}>Update</button>
                                                <button className='btn btn-outline-danger btn-sm' onClick={() =>
                                                    deleteJobApplication(jobApplication.id)} style={{ marginLeft: '12px' }}>Delete</button>
                                            </td>
                                        </tr>
                                    )
                                }
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default ListJobApplications