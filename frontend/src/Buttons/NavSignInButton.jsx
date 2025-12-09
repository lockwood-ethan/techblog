import React, { useState} from 'react';

function SignInButton() {

    const loginUrl = import.meta.env.VITE_LOGIN_URL;

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    const [showLogIn, setShowLogIn] = useState(true);

    const handleClick = () => {
        setShowLogIn(!showLogIn)
    };

    const authenticateUsername = async (username, password) => {

        const postBody = { 
            "username": username,
            "password": password
         }

        await fetch(loginUrl, {
            method: 'POST',
            body: JSON.stringify(postBody),
            headers: {'Content-type': 'application/json'}
        })
        .then(response => {
            console.log(response.body);
            return response.json();
        })
        .then(data => {
            console.log(data);
        })
        .catch(err => {
            console.log(err.message);
        })
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        authenticateUsername(username, password);
    };

    return(
        <>
            { showLogIn ? 
                <button id="navSignInButton" onClick={(e) => handleClick(e)}
                className="btn btn-outline-light text-light text-nowrap me-2">Sign In</button> :
            <> 
                <form className="d-inline-flex" onSubmit={handleSubmit}>
                    <button type="submit" id="navSubmitButton" className="btn btn-outline-light text-light mx-2">Submit</button>
                    <input value={username} onChange={(e) => setUsername(e.target.value)} id="navUsernameTextBox" className="form-control text-light" type="text" placeholder="Username" autoComplete="username"></input>
                    <input value={password} onChange={(e) => setPassword(e.target.value)} id="navPasswordTextBox" className="form-control text-light mx-2" type="password" placeholder="Password" autoComplete="current-password"></input>
                </form>
            </> }
        </>
    );
}

export default SignInButton