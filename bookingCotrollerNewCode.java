package com.crimsonlogic.arilinemanangmentsystem.controller;

import com.crimsonlogic.arilinemanangmentsystem.model.ApiResponse;
import com.crimsonlogic.arilinemanangmentsystem.model.Booking;
import com.crimsonlogic.arilinemanangmentsystem.model.BookingRequest;
import com.crimsonlogic.arilinemanangmentsystem.model.LoginRequest;
import com.crimsonlogic.arilinemanangmentsystem.exception.CustomException;
import com.crimsonlogic.arilinemanangmentsystem.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user/bookings")
public class BookingRestController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<ApiResponse<Map<String, Object>>> processBooking(
            @RequestBody BookingRequest bookingRequest,
            HttpServletRequest request) {
        try {
            String authHeader = request.getHeader("Authorization");
            Map<String, Object> responseData = bookingService.processBooking(authHeader, bookingRequest);
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
            String authHeader = request.getHeader("Authorization");
            List<Booking> bookings = bookingService.getAllBookingsForUser(authHeader);
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
            String authHeader = request.getHeader("Authorization");
            Booking booking = bookingService.getBookingByIdForUser(authHeader, bookingId);
            return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "Booking retrieved successfully", booking));
        } catch (CustomException e) {
            return errorResponse(e.getStatus(), e.getMessage());
        } catch (Exception e) {
            return errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<ApiResponse<String>> cancelBooking(
            @PathVariable("bookingId") String bookingId,
            HttpServletRequest request) {
        try {
            String authHeader = request.getHeader("Authorization");
            bookingService.cancelBooking(authHeader, bookingId);
            return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "Booking " + bookingId + " cancelled and refund processed successfully."));
        } catch (CustomException e) {
            return errorResponse(e.getStatus(), e.getMessage());
        } catch (Exception e) {
            return errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred during cancellation: " + e.getMessage());
        }
    }

    @DeleteMapping("/{bookingId}/passengers/{passengerId}")
    public ResponseEntity<ApiResponse<String>> cancelPassenger(
            @PathVariable("bookingId") String bookingId,
            @PathVariable("passengerId") String passengerId,
            HttpServletRequest request) {
        try {
            String authHeader = request.getHeader("Authorization");
            bookingService.cancelPassenger(authHeader, bookingId, passengerId);
            return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "Passenger " + passengerId + " in booking " + bookingId + " cancelled and refund processed successfully."));
        } catch (CustomException e) {
            return errorResponse(e.getStatus(), e.getMessage());
        } catch (Exception e) {
            return errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred during passenger cancellation: " + e.getMessage());
        }
    }

    @PostMapping("/{bookingId}/check-in")
    public ResponseEntity<ApiResponse<String>> webCheckIn(
            @PathVariable("bookingId") String bookingId,
            @RequestBody LoginRequest checkInRequest,
            HttpServletRequest request) {
        try {
            String authHeader = request.getHeader("Authorization");
            bookingService.performCheckIn(authHeader, bookingId, checkInRequest.getPassword());
            return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "Check-in successful for booking: " + bookingId));
        } catch (CustomException e) {
            return errorResponse(e.getStatus(), e.getMessage());
        } catch (Exception e) {
            return errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred during check-in");
        }
    }

    private <T> ResponseEntity<ApiResponse<T>> errorResponse(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(new ApiResponse<>("ERROR", message));
    }
}