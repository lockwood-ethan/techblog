const refreshUrl = import.meta.env.VITE_REFRESH_TOKEN_URL;

//TODO: Solve issue with infinite loop on logout, might be issue with endpoint code

const handleTokenRefresh = async () => {
    const response = await fetch(refreshUrl, {
        method: 'POST',
        credentials: "include"
    });
    if (!response.ok) {
        throw new Error("Error: " + response.statusText);
    }
}

export default handleTokenRefresh;