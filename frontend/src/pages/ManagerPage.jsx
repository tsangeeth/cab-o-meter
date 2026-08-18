import { useEffect, useState } from 'react';
import { api } from '../api';

export default function ManagerPage() {
  const [tab, setTab] = useState('pending');
  const [fromDate, setFromDate] = useState('');
  const [toDate, setToDate] = useState('');
  const [rows, setRows] = useState([]);
  const [selected, setSelected] = useState(null);
  const [comments, setComments] = useState('');
  const [error, setError] = useState('');

  async function load(status = tab) {
    const data = await api.managerRequests(status, fromDate, toDate);
    setRows(data);
    setSelected(null);
  }

  useEffect(() => {
    load(tab).catch((err) => setError(err.message));
  }, [tab]);

  async function decide(approved) {
    if (!selected) return;
    try {
      if (approved) {
        await api.approve(selected.id, comments);
      } else {
        await api.reject(selected.id, comments);
      }
      setComments('');
      await load(tab);
    } catch (err) {
      setError(err.message);
    }
  }

  return (
    <div>
      <header className="page-head">
        <div>
          <p className="eyebrow">Manager view</p>
          <h1>Approve team cab requests</h1>
        </div>
      </header>
      <form className="panel inline-filters" onSubmit={(event) => { event.preventDefault(); load(tab).catch((err) => setError(err.message)); }}>
        <label>From date<input type="date" value={fromDate} onChange={(e) => setFromDate(e.target.value)} /></label>
        <label>To date<input type="date" value={toDate} onChange={(e) => setToDate(e.target.value)} /></label>
        <button type="submit">Go</button>
      </form>
      {error ? <p className="error">{error}</p> : null}
      <div className="tabs">
        <button type="button" className={tab === 'pending' ? 'active' : ''} onClick={() => setTab('pending')}>Unapproved</button>
        <button type="button" className={tab === 'approved' ? 'active' : ''} onClick={() => setTab('approved')}>Approved</button>
        <button type="button" className={tab === 'declined' ? 'active' : ''} onClick={() => setTab('declined')}>Declined</button>
      </div>
      <section className="panel">
        <table>
          <thead>
            <tr>
              <th>Request ID</th>
              <th>Requested by</th>
              <th>Dates</th>
              <th>Reason</th>
              <th>Status</th>
            </tr>
          </thead>
          <tbody>
            {rows.map((row) => (
              <tr key={row.id} className={selected?.id === row.id ? 'selected' : ''} onClick={() => setSelected(row)}>
                <td>{row.id}</td>
                <td>{row.employeeName} ({row.employeeId})</td>
                <td>{row.startDate} → {row.endDate}</td>
                <td>{row.reason || '—'}</td>
                <td><span className={`pill ${row.status}`}>{row.status}</span></td>
              </tr>
            ))}
          </tbody>
        </table>
        {tab === 'pending' ? (
          <div className="decision">
            <label>Approve / reject comments
              <textarea value={comments} onChange={(e) => setComments(e.target.value)} rows="3" />
            </label>
            <div className="actions">
              <button type="button" disabled={!selected} onClick={() => decide(true)}>Approve</button>
              <button type="button" className="ghost" disabled={!selected} onClick={() => decide(false)}>Reject</button>
            </div>
          </div>
        ) : null}
      </section>
    </div>
  );
}
