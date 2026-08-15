package com.crimsonlogic.arilinemanangmentsystem.controller;

import com.crimsonlogic.arilinemanangmentsystem.model.Flight;
import com.crimsonlogic.arilinemanangmentsystem.enumrator.FlightStatus;
import com.crimsonlogic.arilinemanangmentsystem.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/flights")
public class FlightRestController {

    @Autowired
    private FlightService flightService;

    @GetMapping
    public ResponseEntity<List<Flight>> getAllFlights() {
        return ResponseEntity.ok(flightService.getAllFlights());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Flight> getFlightById(@PathVariable("id") String id) {
        Flight flight = flightService.getFlightById(id);
        if (flight != null) {
            return ResponseEntity.ok(flight);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/time")
    public ResponseEntity<Void> updateFlightTime(@PathVariable("id") String id,
                                                 @RequestParam("departure") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime departure,
                                                 @RequestParam("arrival") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime arrival) {
        boolean updated = flightService.updateFlightTime(id, departure, arrival);
        if (updated) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Void> updateFlightStatus(@PathVariable("id") String id,
                                                   @RequestParam("status") FlightStatus status) {
        boolean updated = flightService.updateFlightStatus(id, status);
        if (updated) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}