package com.crimsonlogic.arilinemanangmentsystem.dto;

import javax.validation.constraints.NotBlank;

/**
 * Data Transfer Object for password request.
 * Used to transfer data between the client and the server.
 */
public class PasswordRequest {

    @NotBlank(message = "Password is required")
    String password;

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
