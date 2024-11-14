import React, { useState, useEffect } from 'react';
import axios from 'axios';

function EmployeeList({ token, admin }) {
    const [employees, setEmployees] = useState([]);
    const [newEmployee, setNewEmployee] = useState({ name: '', position: '' });
    const [error, setError] = useState('');

    useEffect(() => {
        axios.get('http://localhost:8080/api/employees', {
            headers: { Authorization: `Bearer ${token}` }
        }).then(response => {
            setEmployees(response.data);
        }).catch(err => {
            setError("Failed to fetch employees");
        });
    }, [token]);

    // Handle Add Employee
    const handleAddEmployee = () => {
        if (!admin) {
            alert("You don't have permission to add employees.");
            return;
        }

        axios.post('http://localhost:8080/api/employees/admin/add', newEmployee, {
            headers: { Authorization: `Bearer ${token}` }
        }).then(response => {
            setEmployees([...employees, response.data]);
            setNewEmployee({ name: '', position: '' });
        }).catch(err => {
            setError("Failed to add employee");
        });
    };

    // Handle Delete Employee
    const handleDeleteEmployee = (id) => {
        if (!admin) {
            alert("You don't have permission to delete employees.");
            return;
        }

        axios.delete(`http://localhost:8080/api/employees/admin/delete/${id}`, {
            headers: { Authorization: `Bearer ${token}` }
        }).then(() => {
            setEmployees(employees.filter(employee => employee.id !== id));
        }).catch(err => {
            setError("Failed to delete employee");
        });
    };

    return (
        <div className="container">
            <h2>Employees</h2>
            {error && <div className="alert alert-danger">{error}</div>}
            <table className="table">
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Position</th>
                        {admin && <th>Actions</th>} {/* Show Actions column only if the user is admin */}
                    </tr>
                </thead>
                <tbody>
                    {employees.map(employee => (
                        <tr key={employee.id}>
                            <td>{employee.name}</td>
                            <td>{employee.position}</td>
                            {admin && (
                                <td>
                                    <button
                                        className="btn btn-danger"
                                        onClick={() => handleDeleteEmployee(employee.id)}
                                    >
                                        Delete
                                    </button>
                                </td>
                            )}
                        </tr>
                    ))}
                </tbody>
            </table>

            {/* Add Employee Section (only visible to admin) */}
            {admin && (
                <div>
                    <h3>Add Employee</h3>
                    <input
                        type="text"
                        className="form-control"
                        placeholder="Name"
                        value={newEmployee.name}
                        onChange={(e) => setNewEmployee({ ...newEmployee, name: e.target.value })}
                    />
                    <input
                        type="text"
                        className="form-control"
                        placeholder="Position"
                        value={newEmployee.position}
                        onChange={(e) => setNewEmployee({ ...newEmployee, position: e.target.value })}
                    />
                    <button className="btn btn-primary mt-2" onClick={handleAddEmployee}>
                        Add Employee
                    </button>
                </div>
            )}
        </div>
    );
}

export default EmployeeList;
