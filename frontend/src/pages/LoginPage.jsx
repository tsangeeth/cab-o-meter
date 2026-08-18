import { useState } from 'react';
import { Navigate, useNavigate } from 'react-router-dom';
import { api } from '../api';
import { useSession } from '../App.jsx';

export default function LoginPage() {
  const { user, signIn } = useSession();
  const navigate = useNavigate();
  const [employeeId, setEmployeeId] = useState('PC0014');
  const [password, setPassword] = useState('password');
  const [error, setError] = useState('');
  const [busy, setBusy] = useState(false);

  if (user) {
    return <Navigate to="/requests" replace />;
  }

  async function onSubmit(event) {
    event.preventDefault();
    setBusy(true);
    setError('');
    try {
      const next = await api.login(employeeId, password);
      signIn(next);
      navigate('/requests');
    } catch (err) {
      setError(err.message);
    } finally {
      setBusy(false);
    }
  }

  return (
    <div className="login-wrap">
      <section className="login-hero">
        <p className="eyebrow">Office commute desk</p>
        <h1>Cab-O-Meter</h1>
        <p>Request a pickup, get a manager nod, and let trip planners fill the seats.</p>
      </section>
      <form className="panel login-card" onSubmit={onSubmit}>
        <h2>Sign in</h2>
        <label>
          Emp ID
          <input value={employeeId} onChange={(e) => setEmployeeId(e.target.value)} required />
        </label>
        <label>
          Password
          <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} required />
        </label>
        {error ? <p className="error">{error}</p> : null}
        <button type="submit" disabled={busy}>{busy ? 'Signing in…' : 'Login'}</button>
        <p className="hint">Demo accounts all use password <code>password</code>:</p>
        <ul className="hint-list">
          <li><button type="button" onClick={() => setEmployeeId('PC0014')}>PC0014</button> Employee</li>
          <li><button type="button" onClick={() => setEmployeeId('PC0001')}>PC0001</button> Manager</li>
          <li><button type="button" onClick={() => setEmployeeId('PC0011')}>PC0011</button> Trip manager</li>
          <li><button type="button" onClick={() => setEmployeeId('PC0013')}>PC0013</button> Admin</li>
        </ul>
      </form>
    </div>
  );
}
