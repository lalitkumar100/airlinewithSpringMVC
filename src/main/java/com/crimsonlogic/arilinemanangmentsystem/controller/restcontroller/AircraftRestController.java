package com.crimsonlogic.arilinemanangmentsystem.controller.restcontroller;

import com.crimsonlogic.arilinemanangmentsystem.dto.ApiResponse;
import com.crimsonlogic.arilinemanangmentsystem.model.Aircraft;
import com.crimsonlogic.arilinemanangmentsystem.service.AircraftService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/aircraft")
public class AircraftRestController {

    private final AircraftService aircraftService;

    public AircraftRestController(AircraftService aircraftService) {
        this.aircraftService = aircraftService;
    }

    // =========================================================
    // GET ALL AIRCRAFT
    // =========================================================

    @GetMapping
    public ResponseEntity<ApiResponse<List<Aircraft>>> getAllAircraft() {

        List<Aircraft> aircraftList =
                aircraftService.findAllAircraft();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        "SUCCESS",
                        "Aircraft retrieved successfully",
                        aircraftList
                )
        );
    }


    // =========================================================
    // GET AIRCRAFT BY ID
    // =========================================================

    @GetMapping("/{aircraftId}")
    public ResponseEntity<ApiResponse<Aircraft>> getAircraftById(
            @PathVariable String aircraftId) {

        Aircraft aircraft =
                aircraftService.findById(aircraftId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        "SUCCESS",
                        "Aircraft retrieved successfully",
                        aircraft
                )
        );
    }

 }