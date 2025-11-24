import './home.scss';

import { useNavigate } from "react-router-dom";

const Home=()=>{
    const navigate = useNavigate();   
    return(
        <div className="home-main">
            <div className="container">
                <button type="submit" className='btn' onClick={()=>navigate("/login")}>Login</button>
                <button type="submit" className='btn' onClick={()=>navigate("/register")}>Register</button>
            </div>
        </div>
    )
}

export default Home;