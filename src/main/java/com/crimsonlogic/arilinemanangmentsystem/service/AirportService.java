package com.crimsonlogic.arilinemanangmentsystem.service;

import com.crimsonlogic.arilinemanangmentsystem.dto.AirportDTO;
import com.crimsonlogic.arilinemanangmentsystem.model.Airport;
import java.util.List;

/**
 * Service responsible for airport service business logic.
 * Encapsulates core application rules and data manipulation.
 */
public interface AirportService {
    List<Airport> getAllAirports();
    Airport getAirportByCode(String airportCode);

    /**
     * Retrieves the all airports dto.
     * @return List<AirportDTO> the result of the operation
     */
    List<AirportDTO> getAllAirportsDTO();

    AirportDTO getAirportByCodeDTO(String airportCode);
    
    /**
     * Creates or saves add airport.
     * @param airportDTO the airport dto
     * @return AirportDTO the result of the operation
     */
    AirportDTO addAirport(AirportDTO airportDTO);
}