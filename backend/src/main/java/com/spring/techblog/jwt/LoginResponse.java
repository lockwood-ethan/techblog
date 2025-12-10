package com.spring.techblog.jwt;

import java.util.List;

public class LoginResponse {
    private String jwtAccessToken;
    private String jwtRefreshToken;

    private String username;
    private List<String> roles;

    public LoginResponse(String username, List<String> roles, String jwtAccessToken, String jwtRefreshToken) {
        this.username = username;
        this.roles = roles;
        this.jwtAccessToken = jwtAccessToken;
        this.jwtRefreshToken = jwtRefreshToken;
    }

    public String getJwtAccessToken() {
        return jwtAccessToken;
    }

    public void setJwtAccessToken(String jwtAccessToken) {
        this.jwtAccessToken = jwtAccessToken;
    }

    public String getJwtRefreshToken() { return jwtRefreshToken; }

    public void setJwtRefreshToken(String jwtRefreshToken) { this.jwtRefreshToken = jwtRefreshToken; }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
