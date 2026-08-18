package com.crimsonlogic.arilinemanangmentsystem.service.impl;

import com.crimsonlogic.arilinemanangmentsystem.dao.BookingMapper;
import com.crimsonlogic.arilinemanangmentsystem.dao.PaymentMapper;
import com.crimsonlogic.arilinemanangmentsystem.enumrator.BookingStatus;
import com.crimsonlogic.arilinemanangmentsystem.exception.CustomException;
import com.crimsonlogic.arilinemanangmentsystem.model.*;
import com.crimsonlogic.arilinemanangmentsystem.service.*;
import com.crimsonlogic.arilinemanangmentsystem.utility.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingMapper bookingMapper;

    @Autowired
    private FlightService flightService;

    @Autowired
    private PassengerService passengerService;

    @Autowired
    private WalletService walletService;

    @Autowired
    private PaymentMapper paymentMapper;

    @Override
    @Transactional
    public Booking createBooking(Booking booking, User user) {
        // 1. Validate Flight and Seats
        Flight flight = flightService.getFlightById(booking.getFlightBooked().getFlightId());
        if (flight == null) {
            throw new CustomException("Flight not found", HttpStatus.NOT_FOUND);
        }
        booking.setFlightBooked(flight);

        int passengerCount = booking.getPassengers().size();
        if (passengerCount > 9) {
            throw new CustomException("Cannot book more than 9 seats at once", HttpStatus.BAD_REQUEST);
        }

        int availableSeats = flightService.getAvailableSeats(flight.getFlightId(), booking.getSeatClass());
        if (availableSeats < passengerCount) {
            throw new CustomException("Not enough seats available in " + booking.getSeatClass(), HttpStatus.BAD_REQUEST);
        }

        // 2. Calculate Amount
        double totalAmount = flightService.calculateFare(flight.getFlightId(), booking.getSeatClass()) * passengerCount;
        booking.setAmount(totalAmount);
        booking.setUserbooked(user);
        booking.setBookingId(IdGenerator.generateBookingId());
        booking.setBookingDateTime(LocalDateTime.now());
        booking.setBookingStatus(BookingStatus.CONFIRMED); // Will be confirmed after payment

        // 3. Process Payment First (Payment-First Flow)
        Transaction transaction = walletService.payForBooking(user.getId(), totalAmount);
        if (transaction == null) {
            throw new CustomException("Payment failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // 4. Save Booking
        int rows = bookingMapper.insertBooking(booking);
        if (rows <= 0) {
            throw new CustomException("Failed to save booking", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // 5. Save Passengers
        passengerService.savePassengersForBooking(booking, booking.getPassengers());

        // 6. Record Payment Link
        Payment payment = new Payment();
        payment.setPaymentId(IdGenerator.generatePaymentId());
        payment.setBooking(booking);
        payment.setTransaction(transaction);
        payment.setAmount(totalAmount);
        payment.setPaid(true);
        paymentMapper.insertPayment(payment);

        return booking;
    }

    @Override
    public Booking getBookingById(String bookingId) {
        Booking booking = bookingMapper.getBookingById(bookingId);
        if (booking != null) {
            List<Passenger> passengers = passengerService.getPassengersByBookingId(bookingId);
            booking.setPassengers(new java.util.ArrayList<>(passengers));
        }
        return booking;
    }

    @Override
    public List<Booking> getAllBookingsForUser(String userId) {
        List<Booking> bookings = bookingMapper.getAllBookingsByUserId(userId);
        for (Booking booking : bookings) {
            if (booking.getBookingId() != null) {
                List<Passenger> passengers = passengerService.getPassengersByBookingId(booking.getBookingId());
                booking.setPassengers(new java.util.ArrayList<>(passengers));
            }
        }
        return bookings;
    }
}
