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

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new CustomException("Missing or invalid Authorization header", HttpStatus.UNAUTHORIZED);
        }

        String token = authHeader.substring(7);
        if (!jwtUtil.validateToken(token)) {
            throw new CustomException("Invalid or expired JWT token", HttpStatus.UNAUTHORIZED);
        }

        request.setAttribute("claims", jwtUtil.extractAllClaims(token));
        return true;
    }
}