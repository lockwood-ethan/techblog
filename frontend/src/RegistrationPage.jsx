import {useState} from "react";
import {useNavigate} from "react-router";

function RegistrationPage() {
    const registrationUrl = import.meta.env.VITE_REGISTER_URL

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    const navigate = useNavigate();

    async function registerNewUser(username, password) {
        const jsonUserInfo = {
            "username": username,
            "password": password
        }

        const response = await fetch(registrationUrl, {
            method: "POST",
            body: JSON.stringify(jsonUserInfo),
            headers: {"Content-Type": "application/json"},
            credentials: "include"
        })
        if (!response.ok) {
            throw new Error(response.statusText);
        }
        return response.json();
    }

    const handleSubmit = (e) => {
        e.preventDefault();
        registerNewUser(username, password);
        navigate("/");
    }

    return(
        <>
            <form onSubmit={handleSubmit}>
                <input onChange={(e) => setUsername(e.target.value)} type="username" placeholder="Username"></input>
                <input onChange={(e) => setPassword(e.target.value)} type="password" placeholder="Password"></input>
                <button type="submit">Register</button>
            </form>
        </>
    );
}

export default RegistrationPage;