package com.crimsonlogic.arilinemanangmentsystem.interceptor;

import com.crimsonlogic.arilinemanangmentsystem.enumrator.Role;
import com.crimsonlogic.arilinemanangmentsystem.exception.CustomException;
import com.crimsonlogic.arilinemanangmentsystem.utility.JwtUtil;
import io.jsonwebtoken.Claims;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AdminJwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new CustomException("Missing or invalid Authorization header", HttpStatus.UNAUTHORIZED);
        }

        String token = authHeader.substring(7);
        if (!jwtUtil.validateToken(token)) {
            throw new CustomException("Invalid or expired JWT token", HttpStatus.UNAUTHORIZED);
        }

        Claims claims = jwtUtil.extractAllClaims(token);
        String tokenRole = claims.get("role", String.class);

        // Strictly check if the role is ADMIN
        if (tokenRole == null || !tokenRole.equalsIgnoreCase(Role.ADMIN.name())) {
            throw new CustomException("Access denied: Insufficient privileges (Admin role required)", HttpStatus.FORBIDDEN);
        }

        request.setAttribute("claims", claims);
        return true;
    }
}