package com.spring.techblog.payload.request;

import java.util.Set;

public class RegistrationRequest {
    private String username;
    private String password;
    private Set<String> role;

    public RegistrationRequest(String username, String password, Set<String> role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Set<String> getRole() {
        return role;
    }
}
