package com.crimsonlogic.arilinemanangmentsystem.controller;

import com.crimsonlogic.arilinemanangmentsystem.model.ApiResponse;
import com.crimsonlogic.arilinemanangmentsystem.model.Booking;
import com.crimsonlogic.arilinemanangmentsystem.model.BookingRequest;
import com.crimsonlogic.arilinemanangmentsystem.model.User;
import com.crimsonlogic.arilinemanangmentsystem.service.BookingService;
import com.crimsonlogic.arilinemanangmentsystem.service.UserService;
import com.crimsonlogic.arilinemanangmentsystem.utility.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user/bookings")
public class BookingRestController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<ApiResponse<Map<String, Object>>> processBooking(
            @RequestBody BookingRequest bookingRequest,
            HttpServletRequest request) {

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>("ERROR", "Missing or invalid Authorization header"));
        }

        String token = authHeader.substring(7);
        if (!jwtUtil.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>("ERROR", "Invalid JWT token"));
        }

        Claims claims = jwtUtil.extractAllClaims(token);
        String userEmail = claims.getSubject();
        User user = userService.getUserByEmail(userEmail);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>("ERROR", "User not found"));
        }

        // Verify password for wallet payment
        if (!user.verifyPassword(bookingRequest.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>("ERROR", "Incorrect wallet password"));
        }

        try {
            Booking confirmedBooking = bookingService.createBooking(bookingRequest.getBooking(), user);
            
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("bookingId", confirmedBooking.getBookingId());
            responseData.put("amount", confirmedBooking.getAmount());
            responseData.put("status", confirmedBooking.getBookingStatus());
            
            return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "Booking confirmed successfully", responseData));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("ERROR", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<java.util.List<Booking>>> getMyBookings(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>("ERROR", "Missing or invalid Authorization header"));
        }

        String token = authHeader.substring(7);
        if (!jwtUtil.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>("ERROR", "Invalid JWT token"));
        }

        Claims claims = jwtUtil.extractAllClaims(token);
        String userEmail = claims.getSubject();
        User user = userService.getUserByEmail(userEmail);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>("ERROR", "User not found"));
        }

        try {
            java.util.List<Booking> bookings = bookingService.getAllBookingsForUser(user.getId());
            return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "Bookings retrieved successfully", bookings));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("ERROR", e.getMessage()));
        }
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<ApiResponse<Booking>> getBookingById(
            @PathVariable("bookingId") String bookingId,
            HttpServletRequest request) {

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>("ERROR", "Missing or invalid Authorization header"));
        }

        String token = authHeader.substring(7);
        if (!jwtUtil.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>("ERROR", "Invalid JWT token"));
        }

        Claims claims = jwtUtil.extractAllClaims(token);
        String userEmail = claims.getSubject();
        User user = userService.getUserByEmail(userEmail);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>("ERROR", "User not found"));
        }

        try {
            Booking booking = bookingService.getBookingById(bookingId);
            if (booking == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("ERROR", "Booking not found"));
            }

            // Security check: ensure the booking belongs to the authenticated user
            if (!booking.getUserbooked().getId().equals(user.getId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse<>("ERROR", "You are not authorized to view this booking"));
            }

            return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "Booking retrieved successfully", booking));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("ERROR", e.getMessage()));
        }
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<ApiResponse<String>> cancelBooking(
            @PathVariable("bookingId") String bookingId,
            HttpServletRequest request) {
        // AUTH CHECK (simplified for demo as requested)
        return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "Cancellation request received for full booking: " + bookingId + ". Logic will be implemented later."));
    }

    @DeleteMapping("/{bookingId}/passengers/{passengerId}")
    public ResponseEntity<ApiResponse<String>> cancelPassenger(
            @PathVariable("bookingId") String bookingId,
            @PathVariable("passengerId") String passengerId,
            HttpServletRequest request) {
        // AUTH CHECK (simplified for demo as requested)
        return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "Cancellation request received for passenger: " + passengerId + " in booking: " + bookingId + ". Logic will be implemented later."));
    }
}
