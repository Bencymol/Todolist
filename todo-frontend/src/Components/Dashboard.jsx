import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import './dashboard.scss';

const Dashboard = () => {
    const token = localStorage.getItem('token');
    const [tasks, setTasks] = useState([]);
    const navigate = useNavigate();

    const fetchTasks = async () => {
        const response = await axios.get('http://localhost:8080/admin/tasklist', {
            headers: {
                Authorization: `Bearer ${token}`
            }
        });
        if(response.data.length!==0){
         setTasks(response.data);
        }
        else{
            navigate("/add-task")
        }
    }

    const toggleTask = async (taskId) => {
        setTasks((prevTasks) => {
            const updatedTasks = prevTasks.map((task) =>
                task.id === taskId
                    ? {
                        ...task,
                        status: task.status === "done" ? "pending" : "done"
                    }
                    : task
            );
            const updatedTask = updatedTasks.find((t) => t.id === taskId);
            if (updatedTask) {
                updateTask(taskId, { status: updatedTask.status });
            }

            return updatedTasks;
        });
    }

    const updateTask = async (taskId, task) => {
        const response = await axios.put(`http://localhost:8080/admin/update-task/${taskId}`, task, {
            headers: {
                Authorization: `Bearer ${token}`
            }
        })
    }

    const handleDelete=async(taskId)=>{
        const response = await axios.delete(`http://localhost:8080/admin/delete-task/${taskId}`, {
            headers: {
                Authorization: `Bearer ${token}`
            }
        })

        const updatedTask = tasks.filter((t) => t.id === taskId);
        console.log("updated tasks:",updatedTask);
    }

    useEffect(() => {
        fetchTasks();

    }, [])
    return (
        <div className="dashboard-container">
            <div>
                <button type="submit" className="btn" 
                onClick={() => { navigate("/add-task") }}>
                    Add new task
                </button>
            </div>
            <h2 className="container-heading">Todo List</h2>           
            <ul className="task-list">
                {tasks.map((task) => (
                            <li
                                key={task.id}
                                className={`task-item ${task.status === "done" ? "done" : ""}`}
                            >
                                <div className="task-input">
                                    <input
                                    type="checkbox"
                                    checked={task.status === "done"}
                                    onChange={() => toggleTask(task.id)}
                                    />
                                    <label className="task-name">{task.taskName}</label>
                                </div>
                                <div className="delete-btn">
                                    <button className="btn" onClick={()=>handleDelete(task.id)}>Delete</button>
                                </div>
                                
                            </li>
                        ))}
            </ul>
        </div>
    )
}

export default Dashboard;