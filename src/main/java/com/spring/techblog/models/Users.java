package com.spring.techblog.models;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Value;

@Entity
public class Users {

    @Id
    @Column(unique = true)
    private String username;

    @Column
    private String password;

    @Column
    private boolean enabled;

    @Column
    @Nonnull
    private String role;

    protected Users() {}

    public Users(String username, String password, boolean enabled, String role) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.role = role;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
