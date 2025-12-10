import Navbar from './Navbar/Navbar.jsx';
import Posts from './Post/Posts.jsx';
import AboutCard from './AboutCard/AboutCard.jsx'

function Home() {
    return(
        <>
            <Navbar />
            <div className="row">
                <div className="col-2">
                <AboutCard/>
                </div>
                <div className="col-8">
                <Posts />
                </div>
            </div>
        </>
    );
}

export default Home