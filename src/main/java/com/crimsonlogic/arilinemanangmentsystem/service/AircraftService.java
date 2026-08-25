package com.crimsonlogic.arilinemanangmentsystem.service;

import com.crimsonlogic.arilinemanangmentsystem.dto.AircraftDTO;
import com.crimsonlogic.arilinemanangmentsystem.model.Aircraft;

import java.util.List;

public interface AircraftService {

    List<Aircraft> findAllAircraft();

    Aircraft findById(String aircraftId);


    // New DTO methods
    List<AircraftDTO> findAllAircraftDTO();

    AircraftDTO findByIdDTO(String aircraftId);
}
