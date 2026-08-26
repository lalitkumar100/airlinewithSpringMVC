package com.crimsonlogic.arilinemanangmentsystem.dto;

import com.crimsonlogic.arilinemanangmentsystem.enumrator.BookingStatus;

/**
 * Data Transfer Object for booking confirmation response.
 * Used to transfer data between the client and the server.
 */
public class BookingConfirmationResponse {

    private String bookingId;
    /**
     * The amount.
     */
    private double amount;
    private BookingStatus status;

    // =========================================================
    // CONSTRUCTORS
    // =========================================================

    public BookingConfirmationResponse() {
    }

    public BookingConfirmationResponse(
            String bookingId,
            double amount,
            BookingStatus status) {

        this.bookingId = bookingId;
        this.amount = amount;
        this.status = status;
    }

    // =========================================================
    // GETTERS AND SETTERS
    // =========================================================

    /**
     * Retrieves the booking id.
     * @return String the result of the operation
     */
    public String getBookingId() {
        return bookingId;
    }

    /**
     * Sets the booking id.
     * @param bookingId the booking id
     */
    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    /**
     * Retrieves the amount.
     * @return double the result of the operation
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Sets the amount.
     * @param amount the amount
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Retrieves the status.
     * @return BookingStatus the result of the operation
     */
    public BookingStatus getStatus() {
        return status;
    }

    /**
     * Sets the status.
     * @param status the status
     */
    public void setStatus(BookingStatus status) {
        this.status = status;
    }
}