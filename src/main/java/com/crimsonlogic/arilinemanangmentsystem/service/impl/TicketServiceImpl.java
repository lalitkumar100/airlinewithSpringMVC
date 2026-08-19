package com.crimsonlogic.arilinemanangmentsystem.service.impl;

import com.crimsonlogic.arilinemanangmentsystem.dao.BookingMapper;
import com.crimsonlogic.arilinemanangmentsystem.dao.TicketMapper;
import com.crimsonlogic.arilinemanangmentsystem.enumrator.BookingStatus;
import com.crimsonlogic.arilinemanangmentsystem.enumrator.SeatClass;
import com.crimsonlogic.arilinemanangmentsystem.model.Booking;
import com.crimsonlogic.arilinemanangmentsystem.model.Flight;
import com.crimsonlogic.arilinemanangmentsystem.model.Passenger;
import com.crimsonlogic.arilinemanangmentsystem.model.Ticket;
import com.crimsonlogic.arilinemanangmentsystem.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketMapper ticketMapper;

    @Autowired
    private BookingMapper bookingMapper;

    @Override
    @Transactional
    public void generateTickets(Flight flight) {
        if (flight == null || flight.getAircraft() == null) {
            return;
        }

        List<Booking> allBookings = bookingMapper.getBookingsByFlightId(flight.getFlightId());
        
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

    private int countPassengers(List<Booking> bookings) {
        return bookings.stream().mapToInt(b -> b.getPassengers().size()).sum();
    }

    private void assignSeatsAndGenerate(List<Booking> bookings, SeatClass seatClass, String prefix, int capacity) {
        int currentSeat = 1;
        int totalAssigned = 0;

        for (Booking booking : bookings) {
            List<Passenger> passengers = booking.getPassengers();
            if (passengers == null || passengers.isEmpty()) continue;

            if (totalAssigned + passengers.size() <= capacity) {
                for (Passenger p : passengers) {
                    Ticket ticket = new Ticket();
                    ticket.setBooking(booking);
                    ticket.setPassenger(p);
                    ticket.setSeatClass(seatClass);
                    ticket.setSeatNumber(prefix + "-" + (currentSeat++));
                    ticket.setFare(booking.getAmount() / passengers.size()); // Simple split
                    
                    ticketMapper.insertTicket(ticket);
                }
                booking.setBookingStatus(BookingStatus.CONFIRMED);
                bookingMapper.updateBookingStatus(booking.getBookingId(), BookingStatus.CONFIRMED);
                totalAssigned += passengers.size();
            } else {
                booking.setBookingStatus(BookingStatus.WAITLISTED);
                bookingMapper.updateBookingStatus(booking.getBookingId(), BookingStatus.WAITLISTED);
            }
        }
    }

    @Override
    public List<Ticket> getTicketsByFlight(String flightId) {
        return ticketMapper.getTicketsByFlightId(flightId);
    }
}
