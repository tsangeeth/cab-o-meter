import { useEffect, useState } from 'react';
import { api } from '../api';

export default function TripPlannerPage() {
  const [filters, setFilters] = useState({ startDate: '', endDate: '', employeeId: '', department: '', location: '' });
  const [requests, setRequests] = useState([]);
  const [cabs, setCabs] = useState([]);
  const [selected, setSelected] = useState([]);
  const [cab, setCab] = useState('');
  const [routeName, setRouteName] = useState('Office loop');
  const [error, setError] = useState('');
  const [message, setMessage] = useState('');
  const [trips, setTrips] = useState([]);

  async function search(event) {
    event?.preventDefault();
    setError('');
    try {
      const [found, cabList, planList] = await Promise.all([
        api.tripRequests(filters),
        api.cabs(),
        api.trips(),
      ]);
      setRequests(found);
      setCabs(cabList);
      setTrips(planList);
      if (!cab && cabList[0]) setCab(cabList[0].registrationNumber);
    } catch (err) {
      setError(err.message);
    }
  }

  useEffect(() => {
    search().catch((err) => setError(err.message));
  }, []);

  function toggle(id) {
    setSelected((current) => current.includes(id) ? current.filter((item) => item !== id) : [...current, id]);
  }

  async function assignCab() {
    const chosen = requests.filter((row) => selected.includes(row.id));
    if (!chosen.length || !cab) {
      setError('Select at least one approved request and a cab.');
      return;
    }
    const cabRow = cabs.find((item) => item.registrationNumber === cab);
    try {
      await api.createTrip({
        tripDate: chosen[0].startDate,
        tripTime: chosen[0].loginTime,
        routeName,
        approxTripKms: 16,
        approxTripCost: 640,
        tollCharges: 40,
        pickupOrDrop: 'P',
        cabRegistration: cab,
        driverId: null,
        passengers: chosen.map((row) => ({
          employeeId: row.employeeId,
          employeeName: row.employeeName,
          location: row.teamName,
          address: '',
          distanceInKms: 8,
          costCentre: row.costCentre,
          teamName: row.teamName,
        })),
      });
      setMessage(`Assigned ${cabRow?.modelName || cab} to ${chosen.length} passenger(s).`);
      setSelected([]);
      await search();
    } catch (err) {
      setError(err.message);
    }
  }

  return (
    <div>
      <header className="page-head">
        <div>
          <p className="eyebrow">Plan trip</p>
          <h1>Fill cabs from approved requests</h1>
        </div>
      </header>
      {error ? <p className="error">{error}</p> : null}
      {message ? <p className="ok">{message}</p> : null}
      <form className="panel filters" onSubmit={search}>
        <label>Trip start<input type="date" value={filters.startDate} onChange={(e) => setFilters({ ...filters, startDate: e.target.value })} /></label>
        <label>Trip end<input type="date" value={filters.endDate} onChange={(e) => setFilters({ ...filters, endDate: e.target.value })} /></label>
        <label>Emp. ID<input value={filters.employeeId} onChange={(e) => setFilters({ ...filters, employeeId: e.target.value })} /></label>
        <label>Department<input value={filters.department} onChange={(e) => setFilters({ ...filters, department: e.target.value })} /></label>
        <label>Location<input value={filters.location} onChange={(e) => setFilters({ ...filters, location: e.target.value })} /></label>
        <button type="submit">Search</button>
      </form>
      <section className="panel">
        <h3>Trip results</h3>
        <table>
          <thead>
            <tr>
              <th></th>
              <th>Requestor</th>
              <th>Emp. ID</th>
              <th>From</th>
              <th>To</th>
              <th>Start</th>
              <th>End</th>
              <th>Team</th>
            </tr>
          </thead>
          <tbody>
            {requests.map((row) => (
              <tr key={row.id}>
                <td><input type="checkbox" checked={selected.includes(row.id)} onChange={() => toggle(row.id)} /></td>
                <td>{row.employeeName}</td>
                <td>{row.employeeId}</td>
                <td>{row.startDate}</td>
                <td>{row.endDate}</td>
                <td>{row.loginTime}</td>
                <td>{row.logoutTime}</td>
                <td>{row.teamName}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </section>
      <section className="panel grid-2">
        <div>
          <h3>Cab selector</h3>
          <label>Cab lookup
            <select value={cab} onChange={(e) => setCab(e.target.value)}>
              {cabs.map((item) => (
                <option key={item.registrationNumber} value={item.registrationNumber}>
                  {item.registrationNumber} · {item.modelName} · {item.capacity} seats · {item.defaultDriverName}
                </option>
              ))}
            </select>
          </label>
          <label>Route name<input value={routeName} onChange={(e) => setRouteName(e.target.value)} /></label>
          <button type="button" onClick={assignCab}>Assign cab</button>
        </div>
        <div>
          <h3>Planned trips</h3>
          <ul className="trip-list">
            {trips.map((trip) => (
              <li key={trip.id}>
                <strong>{trip.routeName}</strong>
                <span>{trip.tripDate} {trip.tripTime} · {trip.cabRegistration || 'unassigned'}</span>
                <span>{trip.passengers?.length || 0} passenger(s)</span>
              </li>
            ))}
          </ul>
        </div>
      </section>
    </div>
  );
}
