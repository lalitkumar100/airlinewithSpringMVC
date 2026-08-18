package com.crimsonlogic.arilinemanangmentsystem.service;

import com.crimsonlogic.arilinemanangmentsystem.model.Booking;
import com.crimsonlogic.arilinemanangmentsystem.model.User;

public interface BookingService {
    Booking createBooking(Booking booking, User user);
    Booking getBookingById(String bookingId);
    java.util.List<Booking> getAllBookingsForUser(String userId);
    void performCheckIn(String authHeader, String bookingId, String password);
}
