package com.crimsonlogic.arilinemanangmentsystem.controller;

import com.crimsonlogic.arilinemanangmentsystem.dao.UserMapper;
import com.crimsonlogic.arilinemanangmentsystem.model.LoginRequest;
import com.crimsonlogic.arilinemanangmentsystem.model.LoginResponse;
import com.crimsonlogic.arilinemanangmentsystem.exception.CustomException;
import com.crimsonlogic.arilinemanangmentsystem.model.User;
import com.crimsonlogic.arilinemanangmentsystem.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthRestController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        // 1. Find user by email
        User user = userMapper.findByEmail(loginRequest.getEmail());
        if (user == null) {
            throw new CustomException("Invalid email or password", HttpStatus.UNAUTHORIZED);
        }

        // 2. Verify password using your User model method
        boolean isPasswordValid = user.verifyPassword(loginRequest.getPassword());
        if (!isPasswordValid) {
            throw new CustomException("Invalid email or password", HttpStatus.UNAUTHORIZED);
        }

        // 3. Generate Token containing email and role
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

        // 4. Send response back to Postman
        LoginResponse response = new LoginResponse(token, "Login successful");
        return ResponseEntity.ok(response);
    }
}