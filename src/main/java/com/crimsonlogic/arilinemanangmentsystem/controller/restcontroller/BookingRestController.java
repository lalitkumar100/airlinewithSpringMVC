package com.crimsonlogic.arilinemanangmentsystem.controller.restcontroller;


import com.crimsonlogic.arilinemanangmentsystem.dto.ApiResponse;
import com.crimsonlogic.arilinemanangmentsystem.dto.BookingConfirmationResponse;
import com.crimsonlogic.arilinemanangmentsystem.dto.BookingRequest;
import com.crimsonlogic.arilinemanangmentsystem.dto.PasswordRequest;
import com.crimsonlogic.arilinemanangmentsystem.enumrator.CancelType;
import com.crimsonlogic.arilinemanangmentsystem.model.Booking;
import com.crimsonlogic.arilinemanangmentsystem.model.User;
import com.crimsonlogic.arilinemanangmentsystem.service.AuthService;
import com.crimsonlogic.arilinemanangmentsystem.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/v1/user/bookings")
public class BookingRestController {

    // =========================================================
    // SERVICES
    // =========================================================

    @Autowired
    private BookingService bookingService;

    @Autowired
    private AuthService authService;


    // =========================================================
    // CREATE BOOKING
    // =========================================================

    @PostMapping
    public ResponseEntity<ApiResponse<BookingConfirmationResponse>> processBooking(
            @Valid @RequestBody BookingRequest bookingRequest,
            HttpServletRequest request) {

            User user =  authService.getAuthenticatedUser(request);

            BookingConfirmationResponse confirmation =
                    bookingService.createBooking(
                            bookingRequest,
                            user
                    );

            return ResponseEntity.ok(
                    new ApiResponse<>(
                            "SUCCESS",
                            "Booking confirmed successfully",
                            confirmation
                    )
            ); 
  }


    // =========================================================
    // GET MY BOOKINGS
    // =========================================================

    @GetMapping
    public ResponseEntity<ApiResponse<List<Booking>>> getMyBookings(
            HttpServletRequest request) {

            // Get authenticated user from JWT.
            User user =
                    authService.getAuthenticatedUser(request);

            List<Booking> bookings =
                    bookingService.getAllBookingsForUser(
                            user.getId()
                    );

            return ResponseEntity.ok(
                    new ApiResponse<>(
                            "SUCCESS",
                            "Bookings retrieved successfully",
                            bookings
                    )
            );

    
    }


    // =========================================================
    // GET BOOKING BY ID
    // =========================================================

    @GetMapping("/{bookingId}")
    public ResponseEntity<ApiResponse<Booking>> getBookingById(
            @PathVariable("bookingId") String bookingId) {

            // Retrieve booking.
            Booking booking =
                    bookingService.getBookingById(bookingId);

            return ResponseEntity.ok(
                    new ApiResponse<>(
                            "SUCCESS",
                            "Booking retrieved successfully",
                            booking
                    )
            );  
}

  

    @PatchMapping(
            value = "/{bookingId}/cancel",
            params = "!passenger"
    )
    public ResponseEntity<ApiResponse<String>> cancelBooking(
            @PathVariable("bookingId") String bookingId,
            @Valid @RequestBody PasswordRequest payload,
            HttpServletRequest request) {

            // Get authenticated user from JWT.
            User user =
                    authService.getAuthenticatedUser(request);

            // Extract wallet password from request body.


            // Cancel complete booking.
            bookingService.globalBookingCancel(
                    bookingId,
                    user,
                    payload.getPassword(),
                    CancelType.FULL_BOOKING
            );

            return ResponseEntity.ok(
                    new ApiResponse<>(
                            "SUCCESS",
                            "Booking " + bookingId
                                    + " cancelled and refund processed successfully."
                    )
            ); 
}



    // =========================================================
    // CANCEL PASSENGER FROM BOOKING
    // =========================================================

    @PatchMapping(
            value = "/{bookingId}/cancel",
            params = "passenger"
    )
    public ResponseEntity<ApiResponse<String>> cancelPassenger(
            @PathVariable("bookingId") String bookingId,
            @RequestParam("passenger") String passengerId,
            @Valid @RequestBody PasswordRequest payload,
            HttpServletRequest request) {


            // Get authenticated user from JWT.
            User user =
                    authService.getAuthenticatedUser(request);


                    

            // Cancel specific passenger.
            bookingService.globalBookingCancel(
                    bookingId,
                    passengerId,
                    user,
                    payload.getPassword(),
                    CancelType.PASSENGER
            );

            return ResponseEntity.ok(
                    new ApiResponse<>(
                            "SUCCESS",
                            "Passenger " + passengerId
                                    + " in booking " + bookingId
                                    + " cancelled and refund processed successfully."
                    )
            );  

}


    // =========================================================
    // CHECK-IN
    // =========================================================

    @PatchMapping("/{bookingId}/check-in")
    public ResponseEntity<ApiResponse<String>> checkIn(
            @PathVariable("bookingId") String bookingId,
            @Valid @RequestBody PasswordRequest payload,
            HttpServletRequest request) {

            // Get authenticated user from JWT.
            User user =
                    authService.getAuthenticatedUser(request);

            // Perform check-in.
            bookingService.performCheckIn(
                    bookingId,
                    user,
                    payload.getPassword()
            );

            return ResponseEntity.ok(
                    new ApiResponse<>(
                            "SUCCESS",
                            "Check-in successful for booking: "
                                    + bookingId
                    )
            );
}

}