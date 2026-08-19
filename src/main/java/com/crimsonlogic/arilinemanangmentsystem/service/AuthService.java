package com.crimsonlogic.arilinemanangmentsystem.service;

import com.crimsonlogic.arilinemanangmentsystem.model.LoginRequest;
import com.crimsonlogic.arilinemanangmentsystem.model.LoginResponse;

import javax.servlet.http.HttpServletResponse;

public interface AuthService {
    LoginResponse authenticateUser(LoginRequest loginRequest, HttpServletResponse response);
}