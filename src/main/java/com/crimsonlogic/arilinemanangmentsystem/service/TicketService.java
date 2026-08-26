package com.crimsonlogic.arilinemanangmentsystem.service;

import com.crimsonlogic.arilinemanangmentsystem.model.Flight;
import com.crimsonlogic.arilinemanangmentsystem.model.Ticket;
import java.util.List;

/**
 * Service responsible for ticket service business logic.
 * Encapsulates core application rules and data manipulation.
 */
public interface TicketService {
    void generateTickets(Flight flight);
    List<Ticket> getTicketsByFlight(String flightId);
}
