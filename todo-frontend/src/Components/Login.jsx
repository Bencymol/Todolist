import { useEffect, useState } from 'react'
import axios from "axios";
import './login.scss'
import { useNavigate } from 'react-router-dom';
const Login=()=>{
    const [username,setUsername] = useState('');
    const [password,setPassword] = useState('');
    const navigate = useNavigate();
    const handleLogin=async(e)=>{
        e.preventDefault();
        try{
        const response = await axios.post('http://localhost:8080/login',{username,password});
        localStorage.setItem('token',response.data.token);
        navigate("/dashboard");
        }catch(error){
            alert("Username or Paswword is not correct. Please try again!!!")
        }
       
    } 
    useEffect(()=>{},[]);
    return(
        <div className="login-container">
            <div className="login-card">
                <h2>Login</h2>
                <form className="form-group" onSubmit={handleLogin}>
                    <label htmlFor="">Username</label>
                    <input type="text" 
                    placeholder="Enter username."
                    value={username}
                    onChange={(e)=>setUsername(e.target.value)}
                    />
                    <label htmlFor="">Password</label>
                    <input type="password" 
                    placeholder="Enter password."
                    value={password}
                    onChange={(e)=>setPassword(e.target.value)}
                    />
                    <button className="login-btn" type="submit">Login</button>
                </form>
            </div>

        </div>
    )
}

export default Login;