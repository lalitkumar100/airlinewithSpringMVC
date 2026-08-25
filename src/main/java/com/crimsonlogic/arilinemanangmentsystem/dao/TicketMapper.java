package com.crimsonlogic.arilinemanangmentsystem.dao;

import com.crimsonlogic.arilinemanangmentsystem.model.Ticket;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@org.springframework.stereotype.Repository
public interface TicketMapper extends org.springframework.data.repository.Repository<Ticket, String> {
    Ticket save(Ticket entity);


    default int insertTicket(Ticket ticket) {
        save(ticket);
        return 1;
    }

    @Query("SELECT t FROM Ticket t WHERE t.booking.flightBooked.flightId = :flightId AND t.deleted = false")
    List<Ticket> getTicketsByFlightId(@Param("flightId") String flightId);
}

