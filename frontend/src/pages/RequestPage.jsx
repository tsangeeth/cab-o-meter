import { useEffect, useMemo, useState } from 'react';
import { api } from '../api';
import { useSession } from '../App.jsx';

const DAYS = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'];

const emptyForm = {
  startDate: '',
  endDate: '',
  loginTime: '09:00',
  logoutTime: '18:00',
  reoccurDays: ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday'],
  reason: '',
};

export default function RequestPage() {
  const { user } = useSession();
  const [tab, setTab] = useState('request');
  const [form, setForm] = useState(emptyForm);
  const [filter, setFilter] = useState({ startDate: '', endDate: '' });
  const [rows, setRows] = useState([]);
  const [message, setMessage] = useState('');
  const [error, setError] = useState('');

  const address = user.address || {};

  async function loadRequests() {
    const data = await api.myRequests(filter.startDate, filter.endDate);
    setRows(data);
  }

  useEffect(() => {
    loadRequests().catch((err) => setError(err.message));
  }, []);

  function toggleDay(day) {
    setForm((current) => ({
      ...current,
      reoccurDays: current.reoccurDays.includes(day)
        ? current.reoccurDays.filter((item) => item !== day)
        : [...current.reoccurDays, day],
    }));
  }

  async function submit(event) {
    event.preventDefault();
    setError('');
    try {
      await api.createRequest({
        ...form,
        loginTime: `${form.loginTime}:00`,
        logoutTime: `${form.logoutTime}:00`,
      });
      setMessage('Request submitted for manager review.');
      setForm(emptyForm);
      await loadRequests();
      setTab('cancel');
    } catch (err) {
      setError(err.message);
    }
  }

  async function cancel(id) {
    const reason = window.prompt('Cancellation reason?') || 'Changed plans';
    try {
      await api.cancelRequest(id, reason);
      await loadRequests();
    } catch (err) {
      setError(err.message);
    }
  }

  const visible = useMemo(() => rows, [rows]);

  return (
    <div>
      <header className="page-head">
        <div>
          <p className="eyebrow">Employee desk</p>
          <h1>Cab request / cancellation</h1>
        </div>
      </header>
      <div className="grid-2">
        <section className="panel">
          <h3>Employee info</h3>
          <dl className="facts">
            <div><dt>Emp. ID</dt><dd>{user.employeeId}</dd></div>
            <div><dt>Emp. Name</dt><dd>{user.firstName} {user.lastName}</dd></div>
            <div><dt>Manager</dt><dd>{user.managerName || '—'}</dd></div>
            <div><dt>Cost centre</dt><dd>{user.costCentre}</dd></div>
          </dl>
        </section>
        <section className="panel">
          <h3>Address</h3>
          <dl className="facts">
            <div><dt>Line 1</dt><dd>{address.line1 || '—'}</dd></div>
            <div><dt>Line 2</dt><dd>{address.line2 || '—'}</dd></div>
            <div><dt>Landmark</dt><dd>{address.landmark || '—'}</dd></div>
            <div><dt>Locality</dt><dd>{address.locality || '—'}</dd></div>
            <div><dt>City</dt><dd>{address.city || '—'}</dd></div>
            <div><dt>State</dt><dd>{address.state || '—'}</dd></div>
          </dl>
        </section>
      </div>

      <div className="tabs">
        <button type="button" className={tab === 'request' ? 'active' : ''} onClick={() => setTab('request')}>Request</button>
        <button type="button" className={tab === 'cancel' ? 'active' : ''} onClick={() => setTab('cancel')}>Cancellation</button>
      </div>

      {error ? <p className="error">{error}</p> : null}
      {message ? <p className="ok">{message}</p> : null}

      {tab === 'request' ? (
        <form className="panel" onSubmit={submit}>
          <div className="grid-2">
            <label>Start date<input type="date" value={form.startDate} onChange={(e) => setForm({ ...form, startDate: e.target.value })} required /></label>
            <label>End date<input type="date" value={form.endDate} onChange={(e) => setForm({ ...form, endDate: e.target.value })} required /></label>
            <label>Log in time<input type="time" value={form.loginTime} onChange={(e) => setForm({ ...form, loginTime: e.target.value })} required /></label>
            <label>Logout time<input type="time" value={form.logoutTime} onChange={(e) => setForm({ ...form, logoutTime: e.target.value })} required /></label>
          </div>
          <fieldset>
            <legend>Reoccur days</legend>
            <div className="days">
              {DAYS.map((day) => (
                <label key={day} className="check">
                  <input type="checkbox" checked={form.reoccurDays.includes(day)} onChange={() => toggleDay(day)} />
                  {day.slice(0, 3)}
                </label>
              ))}
            </div>
          </fieldset>
          <label>Reason<textarea value={form.reason} onChange={(e) => setForm({ ...form, reason: e.target.value })} rows="3" /></label>
          <div className="actions">
            <button type="submit">Make request</button>
            <button type="button" className="ghost" onClick={() => setForm(emptyForm)}>Clear</button>
          </div>
        </form>
      ) : (
        <section className="panel">
          <form className="inline-filters" onSubmit={(event) => { event.preventDefault(); loadRequests().catch((err) => setError(err.message)); }}>
            <label>Trip start date<input type="date" value={filter.startDate} onChange={(e) => setFilter({ ...filter, startDate: e.target.value })} /></label>
            <label>Trip end date<input type="date" value={filter.endDate} onChange={(e) => setFilter({ ...filter, endDate: e.target.value })} /></label>
            <button type="submit">Search</button>
          </form>
          <table>
            <thead>
              <tr>
                <th>Start</th>
                <th>End</th>
                <th>Login</th>
                <th>Logout</th>
                <th>Status</th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              {visible.map((row) => (
                <tr key={row.id}>
                  <td>{row.startDate}</td>
                  <td>{row.endDate}</td>
                  <td>{row.loginTime}</td>
                  <td>{row.logoutTime}</td>
                  <td><span className={`pill ${row.cancelled ? 'warn' : row.status}`}>{row.cancelled ? 'cancelled' : row.status}</span></td>
                  <td>
                    {!row.cancelled ? <button type="button" className="ghost" onClick={() => cancel(row.id)}>Cancel</button> : null}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </section>
      )}
    </div>
  );
}
