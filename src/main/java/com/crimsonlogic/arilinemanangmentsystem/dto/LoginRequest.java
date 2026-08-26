package com.crimsonlogic.arilinemanangmentsystem.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * Data Transfer Object for login request.
 * Used to transfer data between the client and the server.
 */
public class LoginRequest {

    /**
     * The email.
     */
    @NotBlank(message = "Email is required")
    @Email(message = "Please enter a valid email address")
    private String email;

    /**
     * The password.
     */
    @NotBlank(message = "Password is required")
    private String password;


    // =========================================================
    // GETTERS AND SETTERS
    // =========================================================

    /**
     * Retrieves the email.
     * @return String the result of the operation
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email.
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retrieves the password.
     * @return String the result of the operation
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}