package com.crimsonlogic.arilinemanangmentsystem.controller;

import com.crimsonlogic.arilinemanangmentsystem.model.ApiResponse;
import com.crimsonlogic.arilinemanangmentsystem.model.Booking;
import com.crimsonlogic.arilinemanangmentsystem.model.BookingRequest;
import com.crimsonlogic.arilinemanangmentsystem.model.LoginRequest;
import com.crimsonlogic.arilinemanangmentsystem.model.User;
import com.crimsonlogic.arilinemanangmentsystem.service.BookingService;
import com.crimsonlogic.arilinemanangmentsystem.service.UserService;
import com.crimsonlogic.arilinemanangmentsystem.exception.CustomException;
import com.crimsonlogic.arilinemanangmentsystem.utility.JwtUtil;
import com.crimsonlogic.arilinemanangmentsystem.enumrator.CancelType;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
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
        try {
            User user = authenticateUser(request);

            if (!user.verifyPassword(bookingRequest.getPassword())) {
                return errorResponse(HttpStatus.UNAUTHORIZED, "Incorrect wallet password");
            }

            Booking confirmedBooking = bookingService.createBooking(bookingRequest.getBooking(), user);

            Map<String, Object> responseData = new HashMap<>();
            responseData.put("bookingId", confirmedBooking.getBookingId());
            responseData.put("amount", confirmedBooking.getAmount());
            responseData.put("status", confirmedBooking.getBookingStatus());

            return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "Booking confirmed successfully", responseData));
        } catch (CustomException e) {
            return errorResponse(e.getStatus(), e.getMessage());
        } catch (Exception e) {
            return errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Booking>>> getMyBookings(HttpServletRequest request) {
        try {
            User user = authenticateUser(request);
            List<Booking> bookings = bookingService.getAllBookingsForUser(user.getId());
            return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "Bookings retrieved successfully", bookings));
        } catch (CustomException e) {
            return errorResponse(e.getStatus(), e.getMessage());
        } catch (Exception e) {
            return errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<ApiResponse<Booking>> getBookingById(
            @PathVariable("bookingId") String bookingId,
            HttpServletRequest request) {
        try {
            User user = authenticateUser(request);
            Booking booking = bookingService.getBookingById(bookingId);

            if (booking == null) {
                return errorResponse(HttpStatus.NOT_FOUND, "Booking not found");
            }

            if (!booking.getUserbooked().getId().equals(user.getId())) {
                return errorResponse(HttpStatus.FORBIDDEN, "You are not authorized to view this booking");
            }

            return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "Booking retrieved successfully", booking));
        } catch (CustomException e) {
            return errorResponse(e.getStatus(), e.getMessage());
        } catch (Exception e) {
            return errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PatchMapping(value = "/{bookingId}/cancel", params = "!passenger")
    public ResponseEntity<ApiResponse<String>> cancelBooking(
            @PathVariable("bookingId") String bookingId,
            @RequestBody Map<String, String> requestBody,
            HttpServletRequest request) {
        try {
            User user = authenticateUser(request);
            String password = extractPassword(requestBody);

            bookingService.globalBookingCancel(bookingId, user, password, CancelType.FULL_BOOKING);

            return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "Booking " + bookingId + " cancelled and refund processed successfully."));
        } catch (CustomException e) {
            return errorResponse(e.getStatus(), e.getMessage());
        } catch (Exception e) {
            return errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred during cancellation: " + e.getMessage());
        }
    }

    @PatchMapping(value = "/{bookingId}/cancel", params = "passenger")
    public ResponseEntity<ApiResponse<String>> cancelPassenger(
            @PathVariable("bookingId") String bookingId,
            @RequestParam("passenger") String passengerId,
            @RequestBody Map<String, String> requestBody,
            HttpServletRequest request) {
        try {
            User user = authenticateUser(request);
            String password = extractPassword(requestBody);

            bookingService.globalBookingCancel(bookingId, passengerId, user, password, CancelType.PASSENGER);

            return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "Passenger " + passengerId + " in booking " + bookingId + " cancelled and refund processed successfully."));
        } catch (CustomException e) {
            return errorResponse(e.getStatus(), e.getMessage());
        } catch (Exception e) {
            return errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred during passenger cancellation: " + e.getMessage());
        }
    }

    @PatchMapping("/{bookingId}/check-in")
    public ResponseEntity<ApiResponse<String>> webCheckIn(
            @PathVariable("bookingId") String bookingId,
            @RequestBody LoginRequest checkInRequest,
            HttpServletRequest request) {
        try {
            User user = authenticateUser(request);

            Booking booking = bookingService.getBookingById(bookingId);
            if (booking == null) {
                return errorResponse(HttpStatus.NOT_FOUND, "Booking not found");
            }

            if (!booking.getUserbooked().getId().equals(user.getId())) {
                return errorResponse(HttpStatus.FORBIDDEN, "You are not authorized to check in for this booking");
            }

            bookingService.performCheckIn(booking, user, checkInRequest.getPassword());

            return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "Check-in successful for booking: " + bookingId));
        } catch (CustomException e) {
            return errorResponse(e.getStatus(), e.getMessage());
        } catch (Exception e) {
            return errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred during check-in: " + e.getMessage());
        }
    }

    // ==================== HELPER METHODS ====================

    private User authenticateUser(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new CustomException("Missing or invalid Authorization header", HttpStatus.UNAUTHORIZED);
        }

        String token = authHeader.substring(7);
        if (!jwtUtil.validateToken(token)) {
            throw new CustomException("Invalid JWT token", HttpStatus.UNAUTHORIZED);
        }

        Claims claims = jwtUtil.extractAllClaims(token);
        String userEmail = claims.getSubject();
        User user = userService.getUserByEmail(userEmail);

        if (user == null) {
            throw new CustomException("User not found", HttpStatus.UNAUTHORIZED);
        }

        return user;
    }

    private String extractPassword(Map<String, String> requestBody) {
        String password = requestBody != null ? requestBody.get("password") : null;
        if (password == null || password.isEmpty()) {
            throw new CustomException("Password is required", HttpStatus.BAD_REQUEST);
        }
        return password;
    }

    private <T> ResponseEntity<ApiResponse<T>> errorResponse(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(new ApiResponse<>("ERROR", message));
    }
}