package com.spring.techblog.payload.response;

import java.util.List;
import java.util.UUID;

public class UserInfoResponse {
    private UUID id;
    private String username;
    private boolean enabled;
    private List<String> roles;

    public UserInfoResponse(UUID id, String username, boolean enabled, List<String> roles) {
        this.id = id;
        this.username = username;
        this.enabled = enabled;
        this.roles = roles;
    }
}
