package com.crimsonlogic.arilinemanangmentsystem.model;

import java.util.List;

public class BookingRequest {
    private String password;
    private Booking booking;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }
}
