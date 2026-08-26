package com.crimsonlogic.arilinemanangmentsystem.service;

import com.crimsonlogic.arilinemanangmentsystem.dto.AircraftDTO;
import com.crimsonlogic.arilinemanangmentsystem.model.Aircraft;

import java.util.List;

/**
 * Service responsible for aircraft service business logic.
 * Encapsulates core application rules and data manipulation.
 */
public interface AircraftService {

    List<Aircraft> findAllAircraft();

    /**
     * Finds by id.
     * @param aircraftId the aircraft id
     * @return Aircraft the result of the operation
     */
    Aircraft findById(String aircraftId);


    // New DTO methods
    /**
     * Finds all aircraft dto.
     * @return List<AircraftDTO> the result of the operation
     */
    List<AircraftDTO> findAllAircraftDTO();

    AircraftDTO findByIdDTO(String aircraftId);
    
    /**
     * Creates or saves add aircraft.
     * @param aircraftDTO the aircraft dto
     * @return AircraftDTO the result of the operation
     */
    AircraftDTO addAircraft(AircraftDTO aircraftDTO);
}
