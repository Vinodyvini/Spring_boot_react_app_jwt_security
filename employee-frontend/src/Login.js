import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom'; // Updated import

function Login({ setToken, setadmin }) {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate(); // Use useNavigate

    const login = async () => {
        try {
            const response = await axios.post('http://localhost:8080/api/login', { username, password });
            console.log(response.data);
            const { token, admin } = response.data;

            // Store the token and isAdmin in localStorage
            localStorage.setItem('token', token);
            localStorage.setItem('isAdmin', admin);

            // Set the token and isAdmin state
            setToken(token);
            setadmin(admin);

            // Redirect to employee list after login
            navigate('/employees');
        } catch (error) {
            console.error('Login failed', error);
        }
    };

    return (
        <div className="container">
            <h2>Login</h2>
            <input 
                onChange={(e) => setUsername(e.target.value)} 
                placeholder="Username" 
            />
            <input 
                type="password" 
                onChange={(e) => setPassword(e.target.value)} 
                placeholder="Password" 
            />
            <button onClick={login}>Login</button>
        </div>
    );
}

export default Login;
