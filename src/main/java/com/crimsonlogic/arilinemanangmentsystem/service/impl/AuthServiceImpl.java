package com.crimsonlogic.arilinemanangmentsystem.service.impl;

import com.crimsonlogic.arilinemanangmentsystem.dao.UserMapper;
import com.crimsonlogic.arilinemanangmentsystem.exception.CustomException;
import com.crimsonlogic.arilinemanangmentsystem.model.LoginRequest;
import com.crimsonlogic.arilinemanangmentsystem.model.LoginResponse;
import com.crimsonlogic.arilinemanangmentsystem.model.User;
import com.crimsonlogic.arilinemanangmentsystem.service.AuthService;
import com.crimsonlogic.arilinemanangmentsystem.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public LoginResponse authenticateUser(LoginRequest loginRequest, HttpServletResponse response) {
        // 1. Find user by email
        User user = userMapper.findByEmail(loginRequest.getEmail());
        if (user == null) {
            throw new CustomException("Invalid email or password", HttpStatus.UNAUTHORIZED);
        }

        // 2. Verify password
        boolean isPasswordValid = user.verifyPassword(loginRequest.getPassword());
        if (!isPasswordValid) {
            throw new CustomException("Invalid email or password", HttpStatus.UNAUTHORIZED);
        }

        // 3. Generate Token containing email and role
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

        // 4. Create HttpOnly cookie for the JWT
        Cookie jwtCookie = new Cookie("jwtToken", token);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(false); // Set to true if using HTTPS
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(86400); // 1 day
        response.addCookie(jwtCookie);

        // 5. Return response
        return new LoginResponse(token, "Login successful");
    }
}