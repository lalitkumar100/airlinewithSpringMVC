package com.crimsonlogic.arilinemanangmentsystem.utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET_KEY_STRING = "I_AM_WORK_IN_CRIMSONLALA_COMPANY_BIT_32_MUST";
    private final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY_STRING.getBytes(StandardCharsets.UTF_8));
    private final long EXPIRATION_TIME = 86400000L;//1 DAY IS VALIDATITY

    // Original generateToken method (if needed elsewhere)


    // New overloaded generateToken method including lastLoginAt claim
    public String generateToken(String email, String role, String lastLoginAt) {
        return Jwts.builder()
                .subject(email)
                .claim("role", role)
                .claim("lastLoginAt", lastLoginAt)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    // Validate and extract claims from token
    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Extract email from token
    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    // Extract specific claim (like lastLoginAt)
    public String extractClaim(String token, String claimKey) {
        return extractAllClaims(token).get(claimKey, String.class);
    }

    public boolean validateToken(String token) {
        try {
            extractAllClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}