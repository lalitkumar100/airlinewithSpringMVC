package com.crimsonlogic.arilinemanangmentsystem.interceptor;

import com.crimsonlogic.arilinemanangmentsystem.exception.CustomException;
import com.crimsonlogic.arilinemanangmentsystem.utility.JwtUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        String token = null;

        // 1. Check Authorization Header
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        }

        // 2. Check Cookie if header is missing
        if (token == null && request.getCookies() != null) {
            for (javax.servlet.http.Cookie cookie : request.getCookies()) {
                if ("jwtToken".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        if (token == null || !jwtUtil.validateToken(token)) {
            // For API requests, throw exception
            if (request.getRequestURI().contains("/api/")) {
                throw new CustomException("Missing or invalid token", HttpStatus.UNAUTHORIZED);
            }
            // For web requests, redirect to login
            response.sendRedirect(request.getContextPath() + "/users/login");
            return false;
        }

        request.setAttribute("claims", jwtUtil.extractAllClaims(token));
        return true;
    }
}