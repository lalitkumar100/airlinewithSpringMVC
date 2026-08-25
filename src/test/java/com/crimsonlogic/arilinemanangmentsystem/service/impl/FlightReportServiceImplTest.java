package com.crimsonlogic.arilinemanangmentsystem.service.impl;

import com.crimsonlogic.arilinemanangmentsystem.enumrator.SeatClass;
import com.crimsonlogic.arilinemanangmentsystem.model.Aircraft;
import com.crimsonlogic.arilinemanangmentsystem.model.Booking;
import com.crimsonlogic.arilinemanangmentsystem.model.Flight;
import com.crimsonlogic.arilinemanangmentsystem.model.Payment;
import com.crimsonlogic.arilinemanangmentsystem.model.Refund;
import com.crimsonlogic.arilinemanangmentsystem.model.RevenueReport;
import com.crimsonlogic.arilinemanangmentsystem.service.BookingService;
import com.crimsonlogic.arilinemanangmentsystem.service.FlightService;
import com.crimsonlogic.arilinemanangmentsystem.service.PaymentService;
import com.crimsonlogic.arilinemanangmentsystem.service.RefundService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FlightReportServiceImplTest {

    @Mock
    private FlightService flightService;
    @Mock
    private BookingService bookingService;
    @Mock
    private PaymentService paymentService;
    @Mock
    private RefundService refundService;

    @InjectMocks
    private FlightReportServiceImpl flightReportService;

    @Test
    public void testGetFlightRevenueReport() {
        Booking b1 = new Booking();
        b1.setBookingId("B1");
        
        Booking b2 = new Booking();
        b2.setBookingId("B2");
        
        when(bookingService.getFlightBookings("F1")).thenReturn(Arrays.asList(b1, b2));
        
        Payment p1 = new Payment();
        p1.setAmount(1000.0);
        p1.setPaid(true);
        when(paymentService.getPaymentByBookingId("B1")).thenReturn(p1);
        
        Refund r2 = new Refund();
        r2.setAmount(500.0);
        when(refundService.getRefundByBookingId("B2")).thenReturn(r2);
        when(paymentService.getPaymentByBookingId("B2")).thenReturn(null);
        when(refundService.getRefundByBookingId("B1")).thenReturn(null);

        RevenueReport report = flightReportService.getFlightRevenueReport("F1");
        
        assertNotNull(report);
        assertEquals("F1", report.getFlightId());
        assertEquals(1000.0, report.getTotalBookingAmount());
        assertEquals(500.0, report.getTotalRefundAmount());
    }

    @Test
    public void testGetAvailableSeats() {
        Flight flight = new Flight();
        Aircraft aircraft = new Aircraft();
        aircraft.setCapacity(100);
        flight.setAircraft(aircraft);
        
        when(flightService.getFlightById("F1")).thenReturn(flight);
        when(bookingService.getBookedSeatCount("F1", SeatClass.ECONOMY_CLASS)).thenReturn(20);

        // Economy class is 50% of 100 = 50 seats. 20 booked, so 30 available.
        int available = flightReportService.getAvailableSeats("F1", SeatClass.ECONOMY_CLASS);
        
        assertEquals(30, available);
    }
}
