package com.crimsonlogic.arilinemanangmentsystem.dto;

import com.crimsonlogic.arilinemanangmentsystem.enumrator.BookingStatus;

public class BookingConfirmationResponse {

    private String bookingId;
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

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }
}