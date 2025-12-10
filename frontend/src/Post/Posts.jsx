import React, { useState, useEffect } from 'react';
import Post from './Post.jsx';

function Posts() {

    const blogPostsUrl = import.meta.env.VITE_BLOG_POSTS_URL;

    const [data, setData] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const getPosts = async () => {
        try {
            const response = await fetch(blogPostsUrl);
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const result = await response.json();
            setData(result);
        } catch (err) {
            setError(err.message);
        } finally {
            setLoading(false);
        }
    };

    getPosts();
    }, [blogPostsUrl]);

    if (loading) return(
        <div>Loading...</div>
    );
    if (error) return(
        <div>Error: {error}</div>
    );
    return(
        <>
            <ul className="list-unstyled">
                <li>
                    {data.map(post => (
                        <Post key={post.id} title={post.title} body={post.body}/>
                        ))};
                </li>
            </ul>
        </>
    );
}

export default Posts