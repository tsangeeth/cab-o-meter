async function request(path, options = {}) {
  const response = await fetch(path, {
    credentials: 'include',
    headers: {
      Accept: 'application/json',
      ...(options.body ? { 'Content-Type': 'application/json' } : {}),
      ...options.headers,
    },
    ...options,
  });
  if (!response.ok) {
    let message = `Request failed (${response.status})`;
    try {
      const payload = await response.json();
      message = payload.message || message;
    } catch {
      /* ignore */
    }
    throw new Error(message);
  }
  if (response.status === 204) {
    return null;
  }
  return response.json();
}

export const api = {
  login: (employeeId, password) => request('/api/login', { method: 'POST', body: JSON.stringify({ employeeId, password }) }),
  logout: () => request('/api/logout', { method: 'POST' }),
  me: () => request('/api/user'),
  employees: (name = '') => request(`/api/employees?name=${encodeURIComponent(name)}`),
  createRequest: (payload) => request('/api/cab-requests', { method: 'POST', body: JSON.stringify(payload) }),
  myRequests: (startDate, endDate) => {
    const params = new URLSearchParams();
    if (startDate) params.set('startDate', startDate);
    if (endDate) params.set('endDate', endDate);
    const query = params.toString();
    return request(`/api/cab-requests${query ? `?${query}` : ''}`);
  },
  cancelRequest: (id, reason) => request(`/api/cab-requests/${id}/cancel`, { method: 'POST', body: JSON.stringify({ reason }) }),
  managerRequests: (status, fromDate, toDate) => {
    const params = new URLSearchParams({ status });
    if (fromDate) params.set('fromDate', fromDate);
    if (toDate) params.set('toDate', toDate);
    return request(`/api/manager/requests?${params}`);
  },
  approve: (id, comments) => request(`/api/manager/requests/${id}/approve`, { method: 'POST', body: JSON.stringify({ comments }) }),
  reject: (id, comments) => request(`/api/manager/requests/${id}/reject`, { method: 'POST', body: JSON.stringify({ comments }) }),
  tripRequests: (filters) => {
    const params = new URLSearchParams();
    Object.entries(filters || {}).forEach(([key, value]) => {
      if (value) params.set(key, value);
    });
    const query = params.toString();
    return request(`/api/trip-requests${query ? `?${query}` : ''}`);
  },
  cabs: () => request('/api/cabs'),
  drivers: () => request('/api/drivers'),
  trips: () => request('/api/trips'),
  createTrip: (payload) => request('/api/trips', { method: 'POST', body: JSON.stringify(payload) }),
};
