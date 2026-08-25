package com.crimsonlogic.arilinemanangmentsystem.service.impl;

import com.crimsonlogic.arilinemanangmentsystem.dao.TicketMapper;
import com.crimsonlogic.arilinemanangmentsystem.enumrator.BookingStatus;
import com.crimsonlogic.arilinemanangmentsystem.enumrator.SeatClass;
import com.crimsonlogic.arilinemanangmentsystem.model.Aircraft;
import com.crimsonlogic.arilinemanangmentsystem.model.Booking;
import com.crimsonlogic.arilinemanangmentsystem.model.Flight;
import com.crimsonlogic.arilinemanangmentsystem.model.Passenger;
import com.crimsonlogic.arilinemanangmentsystem.model.Ticket;
import com.crimsonlogic.arilinemanangmentsystem.service.BookingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TicketServiceImplTest {

    @Mock
    private TicketMapper ticketMapper;

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private TicketServiceImpl ticketService;

    @Test
    public void testGenerateTickets_NullFlightOrAircraft() {
        ticketService.generateTickets(null);
        verify(bookingService, never()).getFlightBookings(anyString());

        Flight flightWithoutAircraft = new Flight();
        ticketService.generateTickets(flightWithoutAircraft);
        verify(bookingService, never()).getFlightBookings(anyString());
    }

    @Test
    public void testGenerateTickets_Success() {
        Flight flight = new Flight();
        flight.setFlightId("F1");
        
        Aircraft aircraft = new Aircraft();
        aircraft.setCapacity(10); // FC:2, BC:3, EC:5
        flight.setAircraft(aircraft);
        
        Booking economyBooking = new Booking();
        economyBooking.setBookingId("B1");
        economyBooking.setSeatClass(SeatClass.ECONOMY_CLASS);
        economyBooking.setAmount(1000.0);
        
        Passenger p1 = new Passenger();
        Passenger p2 = new Passenger();
        economyBooking.setPassengers(new ArrayList<>(Arrays.asList(p1, p2)));
        
        List<Booking> allBookings = new ArrayList<>();
        allBookings.add(economyBooking);
        
        when(bookingService.getFlightBookings("F1")).thenReturn(allBookings);
        
        // This should upgrade to BC since BC is empty and EC takes 2 spots
        // It will assign BC-1 and BC-2
        when(ticketMapper.insertTicket(any(Ticket.class))).thenReturn(1);
        doNothing().when(bookingService).updateBookingStatus("B1", BookingStatus.CONFIRMED);

        ticketService.generateTickets(flight);
        
        verify(ticketMapper, times(2)).insertTicket(any(Ticket.class));
        verify(bookingService, times(1)).updateBookingStatus("B1", BookingStatus.CONFIRMED);
        assertEquals(BookingStatus.CONFIRMED, economyBooking.getBookingStatus());
    }

    @Test
    public void testGetTicketsByFlight() {
        Ticket t1 = new Ticket();
        t1.setTicketId("T1");
        Ticket t2 = new Ticket();
        t2.setTicketId("T2");
        
        when(ticketMapper.getTicketsByFlightId("F1")).thenReturn(Arrays.asList(t1, t2));

        List<Ticket> result = ticketService.getTicketsByFlight("F1");
        
        assertEquals(2, result.size());
        verify(ticketMapper, times(1)).getTicketsByFlightId("F1");
    }
}
