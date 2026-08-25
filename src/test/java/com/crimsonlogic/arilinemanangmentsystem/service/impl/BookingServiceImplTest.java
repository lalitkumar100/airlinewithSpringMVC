package com.crimsonlogic.arilinemanangmentsystem.service.impl;

import com.crimsonlogic.arilinemanangmentsystem.dao.BookingMapper;
import com.crimsonlogic.arilinemanangmentsystem.dto.BookingConfirmationResponse;
import com.crimsonlogic.arilinemanangmentsystem.dto.BookingRequest;
import com.crimsonlogic.arilinemanangmentsystem.enumrator.BookingStatus;
import com.crimsonlogic.arilinemanangmentsystem.enumrator.FlightStatus;
import com.crimsonlogic.arilinemanangmentsystem.enumrator.SeatClass;
import com.crimsonlogic.arilinemanangmentsystem.exception.NullValueException;
import com.crimsonlogic.arilinemanangmentsystem.exception.RecordNotFoundException;
import com.crimsonlogic.arilinemanangmentsystem.model.Booking;
import com.crimsonlogic.arilinemanangmentsystem.model.Flight;
import com.crimsonlogic.arilinemanangmentsystem.model.Passenger;
import com.crimsonlogic.arilinemanangmentsystem.model.Payment;
import com.crimsonlogic.arilinemanangmentsystem.model.User;
import com.crimsonlogic.arilinemanangmentsystem.service.FlightReportService;
import com.crimsonlogic.arilinemanangmentsystem.service.FlightService;
import com.crimsonlogic.arilinemanangmentsystem.service.PassengerService;
import com.crimsonlogic.arilinemanangmentsystem.service.WalletService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceImplTest {

    @Mock
    private BookingMapper bookingMapper;
    
    @Mock
    private FlightService flightService;
    
    @Mock
    private FlightReportService flightReportService;
    
    @Mock
    private PassengerService passengerService;
    
    @Mock
    private WalletService walletService;

    @InjectMocks
    private BookingServiceImpl bookingService;

    @Test
    public void testGetBookingById_Success() {
        Booking b1 = new Booking();
        b1.setBookingId("B1");
        
        when(bookingMapper.getBookingById("B1")).thenReturn(b1);
        when(passengerService.getPassengersByBookingId("B1")).thenReturn(new ArrayList<>());
        
        Booking result = bookingService.getBookingById("B1");
        assertNotNull(result);
        assertEquals("B1", result.getBookingId());
        assertNotNull(result.getPassengers());
    }

    @Test
    public void testGetBookingById_NotFound() {
        when(bookingMapper.getBookingById("B1")).thenReturn(null);
        
        assertThrows(RecordNotFoundException.class, () -> bookingService.getBookingById("B1"));
    }

    @Test
    public void testCreateBooking_Success() {
        BookingRequest req = new BookingRequest();
        req.setFlightId("F1");
        req.setSeatClass(SeatClass.ECONOMY_CLASS);
        Passenger p1 = new Passenger(); p1.setFirstName("John");
        req.setPassengers(Arrays.asList(p1));
        
        User user = new User();
        user.setId("U1");
        
        Flight flight = new Flight();
        flight.setFlightId("F1");
        flight.setDepartureDateTime(LocalDateTime.now().plusDays(1));
        flight.setStatus(FlightStatus.SCHEDULED);
        
        when(flightService.getFlightById("F1")).thenReturn(flight);
        when(flightReportService.getAvailableSeats("F1", SeatClass.ECONOMY_CLASS)).thenReturn(10);
        when(flightService.calculateFare("F1", SeatClass.ECONOMY_CLASS)).thenReturn(5000.0);
        
        when(bookingMapper.insertBooking(any(Booking.class))).thenReturn(1);
        Payment payment = new Payment();
        when(walletService.payForBooking(any(Booking.class), eq(5000.0), eq(user))).thenReturn(payment);
        when(passengerService.savePassengersForBooking(any(Booking.class), anyList())).thenReturn(req.getPassengers());

        BookingConfirmationResponse res = bookingService.createBooking(req, user);
        
        assertNotNull(res);
        assertEquals(BookingStatus.CONFIRMED_NOT_CHECKED_IN, res.getStatus());
        assertEquals(5000.0, res.getAmount());
        
        verify(bookingMapper, times(1)).insertBooking(any(Booking.class));
        verify(walletService, times(1)).payForBooking(any(Booking.class), eq(5000.0), eq(user));
        verify(passengerService, times(1)).savePassengersForBooking(any(Booking.class), anyList());
    }

    @Test
    public void testCreateBooking_NullRequest() {
        assertThrows(NullValueException.class, () -> bookingService.createBooking(null, new User()));
    }
}
