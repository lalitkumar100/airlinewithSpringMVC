package com.crimsonlogic.arilinemanangmentsystem.model;

import com.crimsonlogic.arilinemanangmentsystem.enumrator.BookingStatus;
import com.crimsonlogic.arilinemanangmentsystem.enumrator.SeatClass;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingTest {

    @Test
    public void testBookingGettersAndSetters() {
        Booking booking = new Booking();
        booking.setBookingId("BKG-9999");
        booking.setAmount(15000.0);
        booking.setSeatClass(SeatClass.ECONOMY_CLASS);
        booking.setBookingStatus(BookingStatus.CONFIRMED);
        booking.setBookingDateTime(LocalDateTime.now());
        
        assertEquals("BKG-9999", booking.getBookingId());
        assertEquals(15000.0, booking.getAmount());
        assertEquals(SeatClass.ECONOMY_CLASS, booking.getSeatClass());
        assertEquals(BookingStatus.CONFIRMED, booking.getBookingStatus());
        assertNotNull(booking.getBookingDateTime());
    }

    @Test
    public void testBookingRelationshipsWithMocks() {
        Booking booking = new Booking();
        
        Flight mockFlight = mock(Flight.class);
        User mockUser = mock(User.class);
        Payment mockPayment = mock(Payment.class);
        Passenger mockPassenger = mock(Passenger.class);
        
        ArrayList<Passenger> passengers = new ArrayList<>();
        passengers.add(mockPassenger);
        
        booking.setFlightBooked(mockFlight);
        booking.setUserbooked(mockUser);
        booking.setPayment(mockPayment);
        booking.setPassengers(passengers);
        
        assertEquals(mockFlight, booking.getFlightBooked());
        assertEquals(mockUser, booking.getUserbooked());
        assertEquals(mockPayment, booking.getPayment());
        assertTrue(booking.getPassengers().contains(mockPassenger));
    }

    @Test
    public void testBookingDomainLogic() {
        Booking booking = new Booking();
        booking.setBookingStatus(BookingStatus.CONFIRMED);
        
        booking.checkIn();
        assertEquals(BookingStatus.CHECKED_IN, booking.getBookingStatus());
        
        booking.cancel();
        assertEquals(BookingStatus.CANCELLED, booking.getBookingStatus());
    }
}
