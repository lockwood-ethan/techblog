package com.spring.techblog.jwt;

public class RefreshTokenResponse {
    private String jwtAccessToken;

    public RefreshTokenResponse(String jwtAccessToken) {
        this.jwtAccessToken = jwtAccessToken;
    }

    public String getJwtAccessToken() {
        return jwtAccessToken;
    }

    public void setJwtAccessToken(String jwtAccessToken) {
        this.jwtAccessToken = jwtAccessToken;
    }


}
