package com.spring.techblog.models;

import jakarta.persistence.*;

@Entity
public class Users {

    @Id
    @Column(unique = true)
    private String username;

    @Column
    private String password;

    @Column
    private boolean enabled;

    protected Users() {}

    public Users(String username, String password, boolean enabled) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
