package com.crimsonlogic.arilinemanangmentsystem.controller;

import com.crimsonlogic.arilinemanangmentsystem.model.Airport;
import com.crimsonlogic.arilinemanangmentsystem.service.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/airports")
public class AirportRestController {

    @Autowired
    private AirportService airportService;

    // REST endpoint to get all active airports
    @GetMapping
    public ResponseEntity<List<Airport>> getAllAirportsRest() {
        List<Airport> airports = airportService.getAllAirports();
        return ResponseEntity.ok(airports);
    }

    // REST endpoint to get a single airport by code
    @GetMapping("/{code}")
    public ResponseEntity<Airport> getAirportByCodeRest(@PathVariable("code") String code) {
        Airport airport = airportService.getAirportByCode(code);
        if (airport != null) {
            return ResponseEntity.ok(airport);
        }
        return ResponseEntity.notFound().build();
    }
}