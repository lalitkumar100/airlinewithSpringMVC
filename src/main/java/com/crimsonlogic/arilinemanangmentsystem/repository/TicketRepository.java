package com.crimsonlogic.arilinemanangmentsystem.repository;

import com.crimsonlogic.arilinemanangmentsystem.model.Booking;
import com.crimsonlogic.arilinemanangmentsystem.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, String> {
    /**
     * Action for findByBookingAndDeletedFalse.
     * @param booking input parameter
     * @return List<Ticket> output
     */
    List<Ticket> findByBookingAndDeletedFalse(Booking booking);
}
