package com.crimsonlogic.arilinemanangmentsystem.service;

import com.crimsonlogic.arilinemanangmentsystem.enumrator.CancelType;
import com.crimsonlogic.arilinemanangmentsystem.model.Booking;
import com.crimsonlogic.arilinemanangmentsystem.model.User;

import java.util.List;

public interface BookingService {


    public Booking createBooking(
            Booking booking,
            User user);

    public List<Booking> getAllBookingsForUser(
            String userId);

    public Booking getBookingById(String bookingId);
    /**
     * Performs check-in for a booking.
     *
     * @param booking booking to be checked in
     * @param user authenticated user
     * @param password user's password
     */


    void performCheckIn(
            Booking booking,
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

}
