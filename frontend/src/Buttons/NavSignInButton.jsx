import { useNavigate } from 'react-router-dom';

function SignInButton() {

    const navigate = useNavigate();

    const redirectToSignIn = () => {
        navigate("/signin");
    }

    return(
        <button id="navSignInButton" onClick={redirectToSignIn} className="btn btn-outline-light text-light text-nowrap me-2">Sign In</button>
    );
}

export default SignInButton