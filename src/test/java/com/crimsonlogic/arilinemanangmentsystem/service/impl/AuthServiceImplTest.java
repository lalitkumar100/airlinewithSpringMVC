package com.crimsonlogic.arilinemanangmentsystem.service.impl;

import com.crimsonlogic.arilinemanangmentsystem.dto.LoginRequest;
import com.crimsonlogic.arilinemanangmentsystem.dto.LoginResponse;
import com.crimsonlogic.arilinemanangmentsystem.enumrator.Role;
import com.crimsonlogic.arilinemanangmentsystem.exception.CustomException;
import com.crimsonlogic.arilinemanangmentsystem.model.User;
import com.crimsonlogic.arilinemanangmentsystem.service.UserService;
import com.crimsonlogic.arilinemanangmentsystem.utility.JwtUtil;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {

    @Mock
    private UserService userService;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private HttpServletRequest request;

    @Mock
    private Claims claims;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    public void testAuthenticateUser_Success() {
        LoginRequest req = new LoginRequest();
        req.setEmail("test@test.com");
        req.setPassword("pass");

        User user = mock(User.class);
        when(userService.getUserByEmail("test@test.com")).thenReturn(user);
        when(user.verifyPassword("pass")).thenReturn(true);
        when(user.getRole()).thenReturn(Role.USER);
        when(user.getEmail()).thenReturn("test@test.com");
        
        when(jwtUtil.generateToken(anyString(), anyString(), anyString())).thenReturn("mock-token");

        LoginResponse res = authService.authenticateUser(req);
        
        assertNotNull(res);
        assertEquals("mock-token", res.getToken());
        verify(userService, times(1)).UpdateLoginTime(eq(user), any());
    }

    @Test
    public void testAuthenticateUser_InvalidPassword() {
        LoginRequest req = new LoginRequest();
        req.setEmail("test@test.com");
        req.setPassword("wrong");

        User user = mock(User.class);
        when(userService.getUserByEmail("test@test.com")).thenReturn(user);
        when(user.verifyPassword("wrong")).thenReturn(false);

        assertThrows(CustomException.class, () -> authService.authenticateUser(req));
    }

    @Test
    public void testGetAuthenticatedUser_Success() {
        when(request.getHeader("Authorization")).thenReturn("Bearer valid-token");
        when(jwtUtil.validateToken("valid-token")).thenReturn(true);
        when(jwtUtil.extractAllClaims("valid-token")).thenReturn(claims);
        when(claims.getSubject()).thenReturn("test@test.com");
        
        User user = new User();
        when(userService.getUserByEmail("test@test.com")).thenReturn(user);

        User result = authService.getAuthenticatedUser(request);
        
        assertNotNull(result);
        verify(jwtUtil, times(1)).validateToken("valid-token");
    }
}
