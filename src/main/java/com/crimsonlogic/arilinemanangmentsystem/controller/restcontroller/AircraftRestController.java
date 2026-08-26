package com.crimsonlogic.arilinemanangmentsystem.controller.restcontroller;

import com.crimsonlogic.arilinemanangmentsystem.dto.AircraftDTO;
import com.crimsonlogic.arilinemanangmentsystem.dto.ApiResponse;
import com.crimsonlogic.arilinemanangmentsystem.model.Aircraft;
import com.crimsonlogic.arilinemanangmentsystem.service.AircraftService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST/MVC Controller for managing aircraft rest controller operations.
 * Handles HTTP requests and delegates to the appropriate services.
 */
@RestController
@RequestMapping("/api/v1/aircraft")
public class AircraftRestController {

    /**
     * The aircraft service.
     */
    private final AircraftService aircraftService;

    public AircraftRestController(AircraftService aircraftService) {
        this.aircraftService = aircraftService;
    }

    // =========================================================
    // GET ALL AIRCRAFT
    // =========================================================

    /**
     * Retrieves the all aircraft.
     * @return ResponseEntity<ApiResponse<List<AircraftDTO>>> the result of the operation
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<AircraftDTO>>> getAllAircraft() {

        List<AircraftDTO> aircraftList =
                aircraftService.findAllAircraftDTO();

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
    public ResponseEntity<ApiResponse<AircraftDTO>> getAircraftById(
            @PathVariable String aircraftId) {

        AircraftDTO aircraft =
                aircraftService.findByIdDTO(aircraftId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        "SUCCESS",
                        "Aircraft retrieved successfully",
                        aircraft
                )
        );
    }

 }