package com.crimsonlogic.arilinemanangmentsystem.service;

import com.crimsonlogic.arilinemanangmentsystem.dto.BookingConfirmationResponse;
import com.crimsonlogic.arilinemanangmentsystem.dto.BookingDTO;
import com.crimsonlogic.arilinemanangmentsystem.dto.BookingRequest;
import com.crimsonlogic.arilinemanangmentsystem.enumrator.BookingStatus;
import com.crimsonlogic.arilinemanangmentsystem.enumrator.CancelType;
import com.crimsonlogic.arilinemanangmentsystem.enumrator.SeatClass;
import com.crimsonlogic.arilinemanangmentsystem.model.Booking;
import com.crimsonlogic.arilinemanangmentsystem.model.User;

import java.util.List;

public interface BookingService {


    public BookingConfirmationResponse createBooking(
            BookingRequest bookingRequest,
            User user
    );

    public List<Booking> getAllBookingsForUser(
            String userId);

    public Booking getBookingById(String bookingId);
    /**
     * Performs check-in for a booking.
     *
     * @param bookingId booking to be checked in
     * @param user authenticated user
     * @param password user's password
     */


    void performCheckIn(
             String bookingId,
            User user,
            String password);


    /**
     * Cancels a complete booking.
     *
     * @param bookingId booking identifier
     * @param reason cancellation reason
     * @param refundPercentage refund percentage between 0 and 1
     */
    void cancelBooking(
            String bookingId,
            String reason,
            float refundPercentage);


    /**
     * Cancels a passenger from a booking.
     *
     * @param passengerId passenger identifier
     * @param bookingId booking identifier
     * @param reason cancellation reason
     * @param refundPercentage refund percentage between 0 and 1
     */
    void cancelBookingForPassenger(
            String passengerId,
            String bookingId,
            String reason,
            float refundPercentage);


    /**
     * Performs global authorization for booking cancellation.
     *
     * @param bookingId booking identifier
     * @param user authenticated user
     * @param password user's password
     * @param cancelType cancellation type
     */
    void globalBookingCancel(
            String bookingId,
            User user,
            String password,
            CancelType cancelType);

    void cancelFlightAndRefundAllBookings(String flightId);


    /**
     * Performs global authorization for passenger cancellation.
     *
     * @param bookingId booking identifier
     * @param passengerId passenger identifier
     * @param user authenticated user
     * @param password user's password
     * @param cancelType cancellation type
     */
    void globalBookingCancel(
            String bookingId,
            String passengerId,
            User user,
            String password,
            CancelType cancelType);

    int getBookedSeatCount(String flightId, SeatClass seatClass);

    List<Booking> getFlightBookings(String flightId);

    void updateBookingStatus(String bookingId, BookingStatus status);


    // New DTO methods
    BookingDTO getBookingByIdDTO(String bookingId);

    List<BookingDTO> getAllBookingsForUserDTO(String userId);

    List<BookingDTO> getFlightBookingsDTO(String flightId);
}
