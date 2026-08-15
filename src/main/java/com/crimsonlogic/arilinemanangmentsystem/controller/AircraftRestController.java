package com.crimsonlogic.arilinemanangmentsystem.controller;

import com.crimsonlogic.arilinemanangmentsystem.model.Aircraft;
import com.crimsonlogic.arilinemanangmentsystem.service.AircraftService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/aircraft")
public class AircraftRestController {

    private final AircraftService aircraftService;

    @Autowired
    public AircraftRestController(AircraftService aircraftService) {
        this.aircraftService = aircraftService;
    }

    // Get all aircraft
    @GetMapping
    public List<Aircraft> getAllAircraft() {
        return aircraftService.findAllAircraft();
    }

    // Get aircraft by ID
    @GetMapping("/{aircraftId}")
    public Aircraft getAircraftById(
            @PathVariable String aircraftId) {

        return aircraftService.findById(aircraftId);
    }
}