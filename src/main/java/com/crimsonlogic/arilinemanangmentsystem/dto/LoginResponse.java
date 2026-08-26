package com.crimsonlogic.arilinemanangmentsystem.dto;

/**
 * Data Transfer Object for login response.
 * Used to transfer data between the client and the server.
 */
public class LoginResponse {
    private String token;
    private String message;
    /**
     * The role.
     */
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
    /**
     * Retrieves the token.
     * @return String the result of the operation
     */
    public String getToken() {
        return token;
    }

    /**
     * Retrieves the message.
     * @return String the result of the operation
     */
    public String getMessage() {
        return message;
    }

    /**
     * Retrieves the role.
     * @return String the result of the operation
     */
    public String getRole() {
        return role;
    }

    /**
     * Retrieves the last login at.
     * @return String the result of the operation
     */
    public String getLastLoginAt() {
        return lastLoginAt;
    }
}