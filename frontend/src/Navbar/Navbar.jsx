import './Navbar.css'
import NavSignInButton from '../Buttons/NavSignInButton';
import NavRegisterButton from '../Buttons/NavRegisterButton';

function Navbar() {
    return(
    <nav id="homeNav" className="navbar navbar-expand-lg m-2 rounded-4 shadow">
        <div className="container-fluid">
            <div className="container-fluid row align-items-center">
                <div className="col-md-auto">
                    <a className="navbar-brand text-light" href="#">
                        <img className="d-inline-flex mx-2 rounded-5 align-text-center" src="src/assets/studyingProgrammer.png" height="60" alt="logo"></img>
                        Techn(olog)ically A Failure &#123;Blog&#125;
                    </a>
                </div>
                <div className="col"></div>
                <div className="col-md-auto">
                    <NavSignInButton />
                    <NavRegisterButton />
                </div>
                <div className="col-md-auto">
                    <form className="d-flex" role="search">
                        <input id="searchTextBox" className="form-control text-light" type="search" placeholder="Search" aria-label="Search"></input>
                        <button id="navSearchButton" className="btn btn-outline-light text-light mx-3">Search</button>
                    </form>
                </div>
            </div>
        </div>
    </nav>
    );
}

export default Navbar