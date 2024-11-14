import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Login from './Login';
import EmployeeList from './EmployeeList';
import 'bootstrap/dist/css/bootstrap.min.css';

function App() {
  const [token, setToken] = useState(localStorage.getItem('token') || null); // Retrieve token from localStorage
  const [admin, setadmin] = useState(localStorage.getItem('isAdmin') === 'true'); // Retrieve isAdmin from localStorage

  useEffect(() => {
    if (token) {
      // If token is available, ensure isAdmin is checked from localStorage
      const adminStatus = localStorage.getItem('isAdmin') === 'true';
      setadmin(adminStatus);
    }
  }, [token]); // Re-run when the token changes

  return (
    <Router>
      <div className="container">
        <Routes>
          <Route path="/login" element={<Login setToken={setToken} setadmin={setadmin} />} />
          <Route path="/employees" element={<EmployeeList token={token} admin={admin} />} />
          <Route path="/" element={<h2>Welcome</h2>} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
