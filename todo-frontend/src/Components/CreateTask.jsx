import { useState } from 'react';
import './createtask.scss';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const CreateTask = () =>{
    const token = localStorage.getItem('token');
    const user = JSON.parse(localStorage.getItem('user'));
    const [taskName,setTaskName] = useState('');
    const [status,setTaskStatus] = useState('');
    const navigate = useNavigate();
    const handleAddTask=async(e)=>{
        e.preventDefault();
       
        try{
             console.log("create task");
        const response = await axios.post('http://localhost:8080/admin/add-task',{taskName,status,user},{
            headers: {
                Authorization: `Bearer ${token}`
            }
        });
        console.log("task response",response);
        navigate("/dashboard");
        }catch(error){
            alert("Something went wrong. Please try again!!!")
        }
       
    } 
    return(
        <div className="create-task-container">         
            <div className="task-card">
                
            <form className="add-form-group" onSubmit={handleAddTask}>
                <label htmlFor="">Task Name</label>
                    <input type="text" 
                    placeholder="Enter task."
                    value={taskName}
                    onChange={(e)=>setTaskName(e.target.value)}
                    />
                    <label htmlFor="">Status</label>
                    <input type="text" 
                    placeholder="Enter status('pending/done')."
                    value={status}
                    onChange={(e)=>setTaskStatus(e.target.value)}
                    />
                    <button className="add-task-btn" type="submit">Add Task</button>

            </form>
            </div>
        </div>
    )
}

export default CreateTask;