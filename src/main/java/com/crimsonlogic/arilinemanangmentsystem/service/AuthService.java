package com.crimsonlogic.arilinemanangmentsystem.service;

import com.crimsonlogic.arilinemanangmentsystem.dto.LoginRequest;
import com.crimsonlogic.arilinemanangmentsystem.dto.LoginResponse;
import com.crimsonlogic.arilinemanangmentsystem.model.User;

import javax.servlet.http.HttpServletRequest;

/**
 * Service responsible for auth service business logic.
 * Encapsulates core application rules and data manipulation.
 */
public interface AuthService {
    LoginResponse authenticateUser(LoginRequest loginRequest);

    /**
     * Retrieves the authenticated user.
     * @param request the request
     * @return User the result of the operation
     */
    public User getAuthenticatedUser(HttpServletRequest request);
}