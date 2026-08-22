package com.crimsonlogic.arilinemanangmentsystem.service;

import com.crimsonlogic.arilinemanangmentsystem.dto.LoginRequest;
import com.crimsonlogic.arilinemanangmentsystem.dto.LoginResponse;
import com.crimsonlogic.arilinemanangmentsystem.model.User;

import javax.servlet.http.HttpServletRequest;

public interface AuthService {
    LoginResponse authenticateUser(LoginRequest loginRequest);

    public User getAuthenticatedUser(HttpServletRequest request);
}