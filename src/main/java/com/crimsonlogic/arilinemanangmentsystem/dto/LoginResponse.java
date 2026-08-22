package com.crimsonlogic.arilinemanangmentsystem.dto;

public class LoginResponse {
    private String token;
    private String message;
    private String role;
    private String lastLoginAt;

    // Constructor with all fields
    public LoginResponse(String token, String message, String role, String lastLoginAt) {
        this.token = token;
        this.message = message;
        this.role = role;
        this.lastLoginAt = lastLoginAt;
    }

    // Getters
    public String getToken() {
        return token;
    }

    public String getMessage() {
        return message;
    }

    public String getRole() {
        return role;
    }

    public String getLastLoginAt() {
        return lastLoginAt;
    }
}