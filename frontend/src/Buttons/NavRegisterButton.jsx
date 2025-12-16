import {useNavigate} from "react-router";

function NavRegisterButton() {
    const navigate = useNavigate();

    const redirectToRegistration = () => {
        navigate("/register");
    }

    return(
        <button id="navSignInButton" onClick={redirectToRegistration} className="btn btn-outline-light text-light text-nowrap me-2">Register</button>
    );
}

export default NavRegisterButton;