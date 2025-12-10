import profilePic from '../assets/myFace.jpg'
import './AboutCard.css'

function AboutCard() {
    return(
        <div id="aboutCard" className="mw-100 p-2 m-2 rounded-5 text-light shadow">
            <img id="profilePic" className="img-fluid rounded-5" src={profilePic} alt="picture of me"></img>
            <h2 className="text-center m-2">Welcome to my blog!</h2>
            <p>You can find all of my technological endeavors documented here.</p>
        </div>
    );
}

export default AboutCard