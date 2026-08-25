package com.crimsonlogic.arilinemanangmentsystem.model;

import com.crimsonlogic.arilinemanangmentsystem.enumrator.SeatClass;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TicketTest {

    @Test
    public void testTicketGettersAndSetters() {
        Ticket ticket = new Ticket();
        ticket.setTicketId("TKT-999");
        ticket.setFare(3500.0);
        ticket.setSeatClass(SeatClass.BUSINESS_CLASS);
        ticket.setSeatNumber("12A");
        ticket.setCreatedAt(LocalDateTime.now());
        
        assertEquals("TKT-999", ticket.getTicketId());
        assertEquals(3500.0, ticket.getFare());
        assertEquals(SeatClass.BUSINESS_CLASS, ticket.getSeatClass());
        assertEquals("12A", ticket.getSeatNumber());
        assertNotNull(ticket.getCreatedAt());
    }

    @Test
    public void testTicketRelationshipsWithMocks() {
        Ticket ticket = new Ticket();
        
        Booking mockBooking = mock(Booking.class);
        Passenger mockPassenger = mock(Passenger.class);
        
        ticket.setBooking(mockBooking);
        ticket.setPassenger(mockPassenger);
        
        assertEquals(mockBooking, ticket.getBooking());
        assertEquals(mockPassenger, ticket.getPassenger());
    }

    @Test
    public void testTicketDomainLogic() {
        Ticket ticket = new Ticket();
        ticket.setTicketId("TKT-555");
        ticket.setFare(1200.0);
        ticket.setSeatNumber("14B");
        
        String rowString = ticket.toRow();
        String toStringOutput = ticket.toString();
        
        assertTrue(rowString.contains("TKT-555"));
        assertTrue(rowString.contains("14B"));
        assertTrue(toStringOutput.contains("1200.0"));
    }
}
