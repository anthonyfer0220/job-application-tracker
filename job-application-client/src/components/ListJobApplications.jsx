import React from 'react'
import { useNavigate } from 'react-router-dom'

function formatDate(dateStr) {
    if (!dateStr) return '';
    const [year, month, day] = dateStr.split('-');
    return `${month}/${day}/${year}`;
}

const ListJobApplications = () => {

    const dummyData = [
        {
            "id": 1,
            "companyName": "Amazon",
            "position": "Intern",
            "dateApplied": "2025-09-08",
            "oaDate": "2025-12-08",
            "latestInterviewDate": "2025-12-08",
            "finalDecision": "PENDING"
        },
        {
            "id": 2,
            "companyName": "Google",
            "position": "Intern",
            "dateApplied": "2025-11-08",
            "oaDate": "2025-12-08",
            "latestInterviewDate": null,
            "finalDecision": "PENDING"
        },
        {
            "id": 3,
            "companyName": "Skype",
            "position": "Intern",
            "dateApplied": "2025-12-08",
            "oaDate": null,
            "latestInterviewDate": null,
            "finalDecision": "PENDING"
        },
    ]

    const navigate = useNavigate();

    function addNewJobApplication() {
        navigate('/dashboard/add')
    }

    function updateJobApplication(id) {
        navigate(`/dashboard/edit/${id}`)
    }

    function deleteJobApplication(id) {
        console.log(id);
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
                                    dummyData.map(jobApplication =>
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