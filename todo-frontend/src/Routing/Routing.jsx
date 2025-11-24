import { Route, Routes } from "react-router-dom"
import Home from "../Components/Home"
import Login from "../Components/Login";
import Register from "../Components/Register";
import Dashboard from "../Components/Dashboard";
import CreateTask from "../Components/CreateTask";

const Routing=()=>{
    return(
        
            <Routes>
                <Route path="/" element={<Home/>} />
                <Route path="/login" element={<Login/>} />
                <Route path="/register" element={<Register/>} />
                <Route path="/dashboard" element={<Dashboard/>} />
                <Route path="/add-task" element={<CreateTask/>} />
            </Routes>
       
    )
}

export default Routing;