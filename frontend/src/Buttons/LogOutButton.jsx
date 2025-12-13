function LogoutButton() {

    const logoutUrl = import.meta.env.VITE_LOGOUT_URL;

    const logout = async () => {
        await fetch(logoutUrl, {
            method: "POST",
            headers: { 
                "Content-Type": "application/json",
            },
            credentials: "include"
        })
        .then(response => response.json())
        .then(data => {
            console.log("Success:", data);
        })
        .catch(error => {
            console.log("Error:", error)
        });
    }

    return (
        <button onClick={logout}>Logout</button>
    );
}

export default LogoutButton