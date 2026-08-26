package com.crimsonlogic.arilinemanangmentsystem.controller.restcontroller;

import com.crimsonlogic.arilinemanangmentsystem.dto.ApiResponse;
import com.crimsonlogic.arilinemanangmentsystem.dto.LoginRequest;
import com.crimsonlogic.arilinemanangmentsystem.dto.LoginResponse;
import com.crimsonlogic.arilinemanangmentsystem.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * REST/MVC Controller for managing auth rest controller operations.
 * Handles HTTP requests and delegates to the appropriate services.
 */
@RestController
@RequestMapping("/api/v1")
public class AuthRestController {

    /**
     * The auth service.
     */
    private final AuthService authService;

    public AuthRestController(AuthService authService) {
        this.authService = authService;
    }




    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
    		@Valid @RequestBody LoginRequest loginRequest) {

        LoginResponse loginResponse =
                authService.authenticateUser(loginRequest);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        "SUCCESS",
                        "Login successful",
                        loginResponse
                )
        );
    }
}