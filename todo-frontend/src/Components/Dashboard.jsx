import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

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
        setTasks(response.data);
    }

    const toggleTask = async (taskId) => {
        const prev = tasks;

        // const updated = tasks.map((task)=>(
        //     task.id == taskId?{...task,status: t.status === "done" ? "pending" : "done" }          
        // : task));

        // setTasks(updated);
        try {
            // call updateTask with the new status
            const response = await updateTask(taskId, { status: newStatus });
            setTasks(response.data);
        } catch (err) {
            setTasks(prev);
        }
    }

    const updateTask = async (taskId, task) => {
        const response = await axios.put(`http://localhost:8080/admin/update-task/${taskId}`, task, {
            headers: {
                Authorization: `Bearer ${token}`
            }
        })
    }

    useEffect(() => {
        fetchTasks();

    }, [])
    return (
        <div className="dashboard-container">
            <h2>Todo List</h2>
            {tasks.length == 0 && <div><button type="submit"
                className="btn" onClick={() => { navigate("/add-task") }}>Add new task</button></div>}
            <ul className="task-list">
                {tasks.map((task) => (
                    <li key={task.id} className={`task-item${task.status == 'done' ? "done" : ""}`}>
                        <input
                            type="checkbox"
                            checked={task.status === "done"}
                            onChange={() => toggleTask(task.id)}
                        />
                    </li>
                ))}
            </ul>
        </div>
    )
}

export default Dashboard;