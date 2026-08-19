package com.crimsonlogic.arilinemanangmentsystem.controller;

import com.crimsonlogic.arilinemanangmentsystem.model.Booking;
import com.crimsonlogic.arilinemanangmentsystem.model.Flight;
import com.crimsonlogic.arilinemanangmentsystem.model.RevenueReport;
import com.crimsonlogic.arilinemanangmentsystem.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminRestController {

    @Autowired
    private FlightService flightService;

    @GetMapping("/flights")
    public ResponseEntity<List<Flight>> getAllFlights() {
        return ResponseEntity.ok(flightService.getAllFlights());
    }

    @GetMapping("/flights/{flightId}")
    public ResponseEntity<Flight> getFlightDetails(@PathVariable String flightId) {
        Flight flight = flightService.getFlightById(flightId);
        if (flight != null) {
            return ResponseEntity.ok(flight);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/flights/{flightId}/bookings")
    public ResponseEntity<List<Booking>> getFlightBookings(@PathVariable String flightId) {
        List<Booking> bookings = flightService.getFlightBookings(flightId);
        if (bookings != null) {
            return ResponseEntity.ok(bookings);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/flights/{flightId}/revenue")
    public ResponseEntity<RevenueReport> getFlightRevenue(@PathVariable String flightId) {
        RevenueReport report = flightService.getFlightRevenueReport(flightId);
        if (report != null) {
            return ResponseEntity.ok(report);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/flights/{flightId}/cancel")
    public ResponseEntity<String> cancelFlight(@PathVariable String flightId) {
        try {
            flightService.cancelFlight(flightId);
            return ResponseEntity.ok("Flight and associated bookings cancelled successfully. Refunds processed.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error cancelling flight: " + e.getMessage());
        }
    }
}
