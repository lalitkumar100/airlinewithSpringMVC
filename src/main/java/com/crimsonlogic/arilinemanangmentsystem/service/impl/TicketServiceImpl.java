package com.crimsonlogic.arilinemanangmentsystem.service.impl;

import com.crimsonlogic.arilinemanangmentsystem.dao.TicketMapper;
import com.crimsonlogic.arilinemanangmentsystem.enumrator.BookingStatus;
import com.crimsonlogic.arilinemanangmentsystem.enumrator.SeatClass;
import com.crimsonlogic.arilinemanangmentsystem.model.Booking;
import com.crimsonlogic.arilinemanangmentsystem.model.Flight;
import com.crimsonlogic.arilinemanangmentsystem.model.Passenger;
import com.crimsonlogic.arilinemanangmentsystem.model.Ticket;
import com.crimsonlogic.arilinemanangmentsystem.service.BookingService;
import com.crimsonlogic.arilinemanangmentsystem.service.TicketService;
import com.crimsonlogic.arilinemanangmentsystem.utility.IdGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service responsible for ticket service impl business logic.
 * Encapsulates core application rules and data manipulation.
 */
@Service
public class TicketServiceImpl implements TicketService {

    /**
     * The ticket mapper.
     */
    private final TicketMapper ticketMapper;
    private final BookingService bookingService;

    public TicketServiceImpl(TicketMapper ticketMapper, BookingService bookingService) {
        this.ticketMapper = ticketMapper;
        this.bookingService = bookingService;
    }

    /**
     * Executes the generate tickets operation.
     * @param flight the flight
     */
    @Override
    @Transactional
    public void generateTickets(Flight flight) {
        if (flight == null || flight.getAircraft() == null) {
            return;
        }

        List<Booking> allBookings = bookingService.getFlightBookings(flight.getFlightId());
        
        int totalCapacity = flight.getAircraft().getCapacity();
        int firstClassCapacity = (int) (totalCapacity * 0.20);
        int businessClassCapacity = (int) (totalCapacity * 0.30);
        int economyClassCapacity = totalCapacity - firstClassCapacity - businessClassCapacity;

        List<Booking> firstClassBookings = allBookings.stream()
                .filter(b -> b.getSeatClass() == SeatClass.FIRST_CLASS)
                .collect(Collectors.toList());
        List<Booking> businessClassBookings = allBookings.stream()
                .filter(b -> b.getSeatClass() == SeatClass.BUSINESS_CLASS)
                .collect(Collectors.toList());
        List<Booking> economyClassBookings = allBookings.stream()
                .filter(b -> b.getSeatClass() == SeatClass.ECONOMY_CLASS)
                .collect(Collectors.toList());

        // Upgrade Business to First Class if space available
        while (countPassengers(firstClassBookings) < firstClassCapacity && !businessClassBookings.isEmpty()) {
            Booking b = businessClassBookings.remove(0);
            b.setSeatClass(SeatClass.FIRST_CLASS);
            firstClassBookings.add(b);
        }

        // Upgrade Economy to Business if space available
        while (countPassengers(businessClassBookings) < businessClassCapacity && !economyClassBookings.isEmpty()) {
            Booking b = economyClassBookings.remove(0);
            b.setSeatClass(SeatClass.BUSINESS_CLASS);
            businessClassBookings.add(b);
        }

        // Assign seats and generate tickets
        assignSeatsAndGenerate(firstClassBookings, SeatClass.FIRST_CLASS, "FC", firstClassCapacity);
        assignSeatsAndGenerate(businessClassBookings, SeatClass.BUSINESS_CLASS, "BC", businessClassCapacity);
        assignSeatsAndGenerate(economyClassBookings, SeatClass.ECONOMY_CLASS, "EC", economyClassCapacity);
    }

    /**
     * Executes the count passengers operation.
     * @param bookings the bookings
     * @return int the result of the operation
     */
    private int countPassengers(List<Booking> bookings) {
        return bookings.stream().mapToInt(b -> b.getPassengers().size()).sum();
    }

    /**
     * Executes the assign seats and generate operation.
     * @param bookings the bookings
     * @param seatClass the seat class
     * @param prefix the prefix
     * @param capacity the capacity
     */
    private void assignSeatsAndGenerate(List<Booking> bookings, SeatClass seatClass, String prefix, int capacity) {
        int currentSeat = 1;
        int totalAssigned = 0;

        for (Booking booking : bookings) {
            List<Passenger> passengers = booking.getPassengers();
            if (passengers == null || passengers.isEmpty()) continue;

            if (totalAssigned + passengers.size() <= capacity) {
                for (Passenger p : passengers) {
                    Ticket ticket = new Ticket();
                    ticket.setTicketId(IdGenerator.generateTicketId());
                    ticket.setBooking(booking);
                    ticket.setPassenger(p);
                    ticket.setSeatClass(seatClass);
                    ticket.setSeatNumber(prefix + "-" + (currentSeat++));
                    ticket.setFare(booking.getAmount() / passengers.size()); // Simple split
                    
                    ticketMapper.insertTicket(ticket);
                }
                booking.setBookingStatus(BookingStatus.CONFIRMED);
                bookingService.updateBookingStatus(booking.getBookingId(), BookingStatus.CONFIRMED);
                totalAssigned += passengers.size();
            } else {
                bookingService.cancelBooking(
                        booking.getBookingId(),
                        "Seat unavailable during ticket generation",
                        1.0f
                );
            }
        }
    }

    /**
     * Retrieves the tickets by flight.
     * @param flightId the flight id
     * @return List<Ticket> the result of the operation
     */
    @Override
    public List<Ticket> getTicketsByFlight(String flightId) {
        return ticketMapper.getTicketsByFlightId(flightId);
    }
}
