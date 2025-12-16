import { useState } from "react";
import {useNavigate} from "react-router";

function SignInPage() {
    const loginUrl = import.meta.env.VITE_LOGIN_URL;

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    const navigate = useNavigate();

    async function signUserIn(username, password){

        const jsonCreds = {
            "username": username,
            "password": password
        }
        let response = await fetch(loginUrl, {
            method: "POST",
            body: JSON.stringify(jsonCreds),
            headers: {
                "Content-Type": "application/json",
            },
            credentials: "include"
        })
        return response.json();
    }

    const handleSubmit = (e) => {
        e.preventDefault();
        signUserIn(username, password);
        navigate("/");
    }


    return(
        <>
            <form onSubmit={handleSubmit}>
                <input onChange={(e) => setUsername(e.target.value)} type="username" placeholder="Username"></input>
                <input onChange={(e) => setPassword(e.target.value)} type="password" placeholder="Password"></input>
                <button type="submit">Sign In</button>
            </form>
        </>
    );
}

export default SignInPage;