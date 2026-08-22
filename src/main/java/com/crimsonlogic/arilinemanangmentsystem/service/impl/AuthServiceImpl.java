package com.crimsonlogic.arilinemanangmentsystem.service.impl;

import com.crimsonlogic.arilinemanangmentsystem.exception.CustomException;
import com.crimsonlogic.arilinemanangmentsystem.dto.LoginRequest;
import com.crimsonlogic.arilinemanangmentsystem.dto.LoginResponse;
import com.crimsonlogic.arilinemanangmentsystem.model.User;
import com.crimsonlogic.arilinemanangmentsystem.service.AuthService;
import com.crimsonlogic.arilinemanangmentsystem.service.UserService;
import com.crimsonlogic.arilinemanangmentsystem.utility.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;


@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public LoginResponse authenticateUser(LoginRequest loginRequest) {
        // 1. Find user and verify password
        User user = userService.getUserByEmail(loginRequest.getEmail());
        if (user == null || !user.verifyPassword(loginRequest.getPassword())) {
            throw new CustomException("Invalid email or password", HttpStatus.UNAUTHORIZED);
        }

        // 2. Generate new last login timestamp (current time)
        LocalDateTime loginTime = LocalDateTime.now();
        user.setLastLoginAt(loginTime);

        // 3. Save this new timestamp in the database (invalidates any older logins)
        userService.UpdateLoginTime(user, loginTime);

        // 4. Generate Token containing email, role, and the lastLoginAt timestamp
        // (Make sure your JwtUtil method accepts this timestamp as a claim)
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name(), loginTime.toString());

        // 5. Return token response
        return new LoginResponse(
                token,
                "Login successful",
                user.getRole().name(),
                loginTime.toString()
        );
    }
        /**
         * Extracts the authenticated user from the JWT
         * present in the Authorization header.
         */
        public User getAuthenticatedUser(HttpServletRequest request) {

            // Get Authorization header.
            String authHeader = request.getHeader("Authorization");

            // Validate Authorization header.
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new CustomException(
                        "Missing or invalid Authorization header",
                        HttpStatus.UNAUTHORIZED
                );
            }

            // Extract JWT token.
            String token = authHeader.substring(7);

            // Validate JWT.
            if (!jwtUtil.validateToken(token)) {
                throw new CustomException(
                        "Invalid JWT token",
                        HttpStatus.UNAUTHORIZED
                );
            }

            // Extract claims from JWT.
            Claims claims = jwtUtil.extractAllClaims(token);

            // Email is stored as JWT subject.
            String userEmail = claims.getSubject();

            // Find user from database.
            User user = userService.getUserByEmail(userEmail);

            // Make sure user still exists.
            if (user == null) {
                throw new CustomException(
                        "User not found",
                        HttpStatus.UNAUTHORIZED
                );
            }

            return user;
        }
    }
