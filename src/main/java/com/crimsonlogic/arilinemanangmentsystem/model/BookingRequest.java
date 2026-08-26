package com.crimsonlogic.arilinemanangmentsystem.model;

import java.util.List;

/**
 * Entity class representing a booking request in the system.
 * Maps to the corresponding database table or domain object.
 */
public class BookingRequest {
    private String password;
    private Booking booking;

    /**
     * Retrieves the password.
     * @return String the result of the operation
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Retrieves the booking.
     * @return Booking the result of the operation
     */
    public Booking getBooking() {
        return booking;
    }

    /**
     * Sets the booking.
     * @param booking the booking
     */
    public void setBooking(Booking booking) {
        this.booking = booking;
    }
}
