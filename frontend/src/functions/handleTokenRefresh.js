const refreshUrl = import.meta.env.VITE_REFRESH_TOKEN_URL;

const handleTokenRefresh = async () => {
    const response = await fetch(refreshUrl, {
        method: 'POST',
        credentials: "include"
    });
    if (!response.ok) {
        throw new Error(response.statusText);
    }
}

export default handleTokenRefresh;