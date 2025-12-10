import './Post.css';

function Post(props) {
    return(
        <div id="postCard" className="rounded-5 p-2 m-2 text-light shadow">
            <h2 className="m-2">{props.title}</h2>
            <p className="m-2">{props.body}</p>
        </div>
    );
}

export default Post