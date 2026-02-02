package com.airin.taskmanager_backend.dto.auth;

public class AuthResponse {
    public String token;
    public String userId;
    public String email;

    public AuthResponse(String token, String userId, String email) {
        this.token = token;
        this.userId = userId;
        this.email = email;
    }
}
