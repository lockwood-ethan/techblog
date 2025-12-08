import React, { useState} from 'react';

function SignInButton() {

    const [showLogIn, setShowLogIn] = useState(true);

    const handleClick = () => {
        setShowLogIn(!showLogIn)
    };

    return(
        <>
            { showLogIn ? 
                <button id="navSignInButton" onClick={(e) => handleClick(e)}
                className="btn btn-outline-light text-light text-nowrap me-2">Sign In</button> :
            <> 
                <form className="d-inline-flex">
                    <button id="navSubmitButton" className="btn btn-outline-light text-light mx-2" type="submit">Submit</button>
                    <input id="navUsernameTextBox" className="form-control text-light" type="text" placeholder="Username"></input>
                    <input id="navPasswordTextBox" className="form-control text-light mx-3" type="password" placeholder="Password"></input>
                </form>
            </> }
        </>
    );
}

export default SignInButton