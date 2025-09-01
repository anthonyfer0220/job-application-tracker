import React, { useState, useEffect } from 'react'
import { useNavigate } from 'react-router-dom'
import { listJobApplications, deleteJobApplication as deleteJobApplicationFromServer } from '../../services/JobApplicationService'

function formatDate(dateStr) {
  if (!dateStr) return '';
  const [year, month, day] = dateStr.split('-');
  return `${month}/${day}/${year}`;
}

function SortIndicator({ active, dir }) {
  if (!active) return null;
  return <span style={{ marginLeft: 4 }}>{dir === 'asc' ? '▲' : '▼'}</span>;
}

const PAGE_SIZE = 10;

const ListJobApplications = () => {

  const [items, setItems] = useState([]);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [totalItems, setTotalItems] = useState(0);
  const [sortField, setSortField] = useState('dateApplied');
  const [sortDir, setSortDir] = useState('desc');
  const [initialized, setInitialized] = useState(false);
  const [error, setError] = useState('');

  const navigate = useNavigate();

  useEffect(() => {
    fetchPage(page, `${sortField},${sortDir}`);
  }, [page, sortField, sortDir]);

  async function fetchPage(p, sort) {
    try {
      setError('');
      const resp = await listJobApplications({ page: p, sort });
      const data = resp?.data || {};

      setItems(data.items || []);
      setTotalPages(data.totalPages ?? 0);
      setTotalItems(data.totalItems ?? 0);
    } catch (err) {
      console.error(err);
      setError('Failed to load job applications.');
    } finally {
      setInitialized(true)
    }
  }

  function addNewJobApplication() {
    navigate('/dashboard/add')
  }

  function updateJobApplication(id) {
    navigate(`/dashboard/edit/${id}`)
  }

  async function deleteJobApplication(id) {
    try {
      await deleteJobApplicationFromServer(id);
      const nextPage = items.length === 1 && page > 0 ? page - 1 : page;
      setPage(nextPage);
    } catch (err) {
      console.error(err);
      setError('Failed to delete the job application.')
    }
  }

  function handleHeaderSort(field) {
    if (sortField === field) {
      setSortDir(sortDir === 'asc' ? 'desc' : 'asc');
    } else {
      setSortField(field);
      setSortDir('asc');
    }
    setPage(0);
  }

  const start = totalItems === 0 ? 0 : page * PAGE_SIZE + 1;
  const end = page * PAGE_SIZE + items.length;
  const blurThen = (fn) => (e) => { e.currentTarget.blur(); fn(); };

  return (
    <div className='dashboard-page'>
      <div className='container'>
        <div className='dashboard-header align-items-center'>
          <h2 className='h3 mb-3'>My Job Applications</h2>
          <div className='d-flex justify-content-center mb-3'>
            <button className='btn btn-brand py-1 px-3 ms-2' onClick={addNewJobApplication}>New Entry</button>
          </div>
        </div>

        {error && <div className='alert alert-soft-danger mb-3'>{error}</div>}

        <div className='dashboard-table'>
          <div className='table-responsive table-wrap'>
            <table className='table table-hover align-middle'>
              <thead className='table-light'>
                <tr>
                  <th style={{ cursor: 'pointer' }} onClick={() => handleHeaderSort('companyName')}>
                    Company Name
                    <SortIndicator active={sortField === 'companyName'} dir={sortDir} />
                  </th>

                  <th style={{ cursor: 'pointer' }} onClick={() => handleHeaderSort('position')}>
                    Position
                    <SortIndicator active={sortField === 'position'} dir={sortDir} />
                  </th>

                  <th style={{ cursor: 'pointer' }} onClick={() => handleHeaderSort('dateApplied')}>
                    Date Applied
                    <SortIndicator active={sortField === 'dateApplied'} dir={sortDir} />
                  </th>

                  <th style={{ cursor: 'pointer' }} onClick={() => handleHeaderSort('oaDate')}>
                    OA Date
                    <SortIndicator active={sortField === 'oaDate'} dir={sortDir} />
                  </th>

                  <th style={{ cursor: 'pointer' }} onClick={() => handleHeaderSort('latestInterviewDate')}>
                    Latest Interview Date
                    <SortIndicator active={sortField === 'latestInterviewDate'} dir={sortDir} />
                  </th>

                  <th style={{ cursor: 'pointer' }} onClick={() => handleHeaderSort('finalDecision')}>
                    Final Decision
                    <SortIndicator active={sortField === 'finalDecision'} dir={sortDir} />
                  </th>

                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                {
                  items.map(jobApplication =>
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

                {initialized && items.length === 0 && (
                  <tr>
                    <td colSpan={7} className='text-center py-4'>
                      No job applications found. Click "New Entry" to add the first one!
                    </td>
                  </tr>
                )}

              </tbody>
            </table>
          </div>

          {initialized && (
            <div className='d-flex flex-column align-items-center gap-1 p-0'>
              <small className='text-muted'>
                Showing {start}-{end} of {totalItems}
              </small>

              <nav>
                <ul className='pagination margimb-0'>
                  <li className={`page-item ${page === 0 ? 'disabled' : ''}`}>
                    <button
                      className='page-link btn-brand border border-1 border-white'
                      onClick={blurThen(() => setPage(0))}>
                      First
                    </button>
                  </li>

                  <li className={`page-item ${page === 0 ? 'disabled' : ''}`}>
                    <button
                      className='page-link btn-brand border border-1 border-white'
                      onClick={blurThen(() => setPage(p => Math.max(0, p - 1)))}>
                      Prev
                    </button>
                  </li>

                  <li className='page-item disabled'>
                    <span
                      className='page-link btn-brand border border-1 border-white'>
                      {totalPages === 0 ? 0 : page + 1} / {totalPages}
                    </span>
                  </li>

                  <li className={`page-item ${page >= totalPages - 1 ? 'disabled' : ''}`}>
                    <button
                      className='page-link btn-brand border border-1 border-white'
                      onClick={blurThen(() => setPage(p => Math.min(totalPages - 1, p + 1)))}>
                      Next
                    </button>
                  </li>

                  <li className={`page-item ${page >= totalPages - 1 ? 'disabled' : ''}`}>
                    <button
                      className='page-link btn-brand border border-1 border-white'
                      onClick={blurThen(() => setPage(Math.max(0, totalPages - 1)))}>
                      Last
                    </button>
                  </li>
                </ul>
              </nav>

            </div>

          )}
        </div>
      </div>
    </div>
  );
};

export default ListJobApplications