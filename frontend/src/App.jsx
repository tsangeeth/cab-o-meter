import { createContext, useContext, useEffect, useMemo, useState } from 'react';
import { Navigate, NavLink, Route, Routes, useLocation, useNavigate } from 'react-router-dom';
import { api } from './api';
import LoginPage from './pages/LoginPage.jsx';
import RequestPage from './pages/RequestPage.jsx';
import ManagerPage from './pages/ManagerPage.jsx';
import TripPlannerPage from './pages/TripPlannerPage.jsx';
import EmployeesPage from './pages/EmployeesPage.jsx';

const SessionContext = createContext(null);
export const useSession = () => useContext(SessionContext);

function canSee(role, item) {
  if (item.roles.includes('All')) return true;
  return item.roles.includes(role);
}

const NAV = [
  { to: '/requests', label: 'Cab request', roles: ['All'] },
  { to: '/manager', label: 'Approvals', roles: ['Manager', 'Admin', 'TripManager'] },
  { to: '/trips', label: 'Trip planner', roles: ['TripManager', 'Admin'] },
  { to: '/employees', label: 'Employees', roles: ['All'] },
];

function Shell({ children }) {
  const { user, signOut } = useSession();
  return (
    <div className="shell">
      <aside className="rail">
        <div className="brand">
          <span className="meter" aria-hidden="true" />
          <div>
            <strong>Cab-O-Meter</strong>
            <p>Commute desk</p>
          </div>
        </div>
        <nav>
          {NAV.filter((item) => canSee(user.role, item)).map((item) => (
            <NavLink key={item.to} to={item.to}>
              {item.label}
            </NavLink>
          ))}
        </nav>
        <div className="who">
          <p className="who-name">{user.firstName} {user.lastName}</p>
          <p className="who-meta">{user.employeeId} · {user.role}</p>
          <button type="button" className="linkish" onClick={signOut}>Sign out</button>
        </div>
      </aside>
      <main className="stage">{children}</main>
    </div>
  );
}

function RequireAuth({ children }) {
  const { user, loading } = useSession();
  const location = useLocation();
  if (loading) {
    return <div className="splash">Loading Cab-O-Meter…</div>;
  }
  if (!user) {
    return <Navigate to="/login" replace state={{ from: location }} />;
  }
  return <Shell>{children}</Shell>;
}

export default function App() {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    api.me()
      .then(setUser)
      .catch(() => setUser(null))
      .finally(() => setLoading(false));
  }, []);

  const value = useMemo(() => ({
    user,
    loading,
    signIn: (next) => setUser(next),
    signOut: async () => {
      try {
        await api.logout();
      } catch {
        /* session already gone */
      }
      setUser(null);
      navigate('/login');
    },
  }), [user, loading, navigate]);

  return (
    <SessionContext.Provider value={value}>
      <Routes>
        <Route path="/login" element={<LoginPage />} />
        <Route path="/requests" element={<RequireAuth><RequestPage /></RequireAuth>} />
        <Route path="/manager" element={<RequireAuth><ManagerPage /></RequireAuth>} />
        <Route path="/trips" element={<RequireAuth><TripPlannerPage /></RequireAuth>} />
        <Route path="/employees" element={<RequireAuth><EmployeesPage /></RequireAuth>} />
        <Route path="*" element={<Navigate to="/requests" replace />} />
      </Routes>
    </SessionContext.Provider>
  );
}
