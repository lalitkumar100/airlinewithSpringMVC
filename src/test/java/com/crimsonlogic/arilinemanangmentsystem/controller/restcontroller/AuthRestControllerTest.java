package com.crimsonlogic.arilinemanangmentsystem.controller.restcontroller;

import com.crimsonlogic.arilinemanangmentsystem.dto.ApiResponse;
import com.crimsonlogic.arilinemanangmentsystem.dto.LoginRequest;
import com.crimsonlogic.arilinemanangmentsystem.dto.LoginResponse;
import com.crimsonlogic.arilinemanangmentsystem.service.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthRestControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthRestController authRestController;

    @Test
    public void testLogin_Success() {
        LoginRequest request = new LoginRequest();
        request.setEmail("user@test.com");
        request.setPassword("password123");

        LoginResponse mockResponse = new LoginResponse("mock-jwt-token", "user@test.com", "USER", "U1");
        
        when(authService.authenticateUser(any(LoginRequest.class))).thenReturn(mockResponse);

        ResponseEntity<ApiResponse<LoginResponse>> response = authRestController.login(request);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("SUCCESS", response.getBody().getStatus());
        assertEquals("mock-jwt-token", response.getBody().getResponseData().getToken());
        
        verify(authService, times(1)).authenticateUser(any(LoginRequest.class));
    }

    @Test
    public void testLogin_AuthServiceCalledWithCorrectData() {
        LoginRequest request = new LoginRequest();
        request.setEmail("admin@test.com");
        request.setPassword("adminpass");

        LoginResponse mockResponse = new LoginResponse("admin-token", "admin@test.com", "ADMIN", "A1");
        
        when(authService.authenticateUser(request)).thenReturn(mockResponse);

        authRestController.login(request);
        
        verify(authService, times(1)).authenticateUser(request);
    }
}
