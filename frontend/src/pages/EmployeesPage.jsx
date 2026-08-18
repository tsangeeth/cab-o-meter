import { useEffect, useState } from 'react';
import { api } from '../api';

export default function EmployeesPage() {
  const [name, setName] = useState('');
  const [rows, setRows] = useState([]);
  const [error, setError] = useState('');

  async function search(event) {
    event?.preventDefault();
    try {
      setRows(await api.employees(name));
    } catch (err) {
      setError(err.message);
    }
  }

  useEffect(() => {
    search().catch((err) => setError(err.message));
  }, []);

  return (
    <div>
      <header className="page-head">
        <div>
          <p className="eyebrow">Directory</p>
          <h1>Find employees</h1>
        </div>
      </header>
      <form className="panel inline-filters" onSubmit={search}>
        <label>Name or emp ID<input value={name} onChange={(e) => setName(e.target.value)} placeholder="Howell, John, PC0014" /></label>
        <button type="submit">Search</button>
      </form>
      {error ? <p className="error">{error}</p> : null}
      <section className="panel">
        <table>
          <thead>
            <tr>
              <th>Emp ID</th>
              <th>Name</th>
              <th>Role</th>
              <th>Team</th>
              <th>Cost centre</th>
              <th>Email</th>
            </tr>
          </thead>
          <tbody>
            {rows.map((row) => (
              <tr key={row.employeeId}>
                <td>{row.employeeId}</td>
                <td>{row.firstName} {row.lastName}</td>
                <td>{row.role}</td>
                <td>{row.teamName}</td>
                <td>{row.costCentre}</td>
                <td>{row.email}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </section>
    </div>
  );
}
