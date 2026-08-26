package com.crimsonlogic.arilinemanangmentsystem.service.impl;

import com.crimsonlogic.arilinemanangmentsystem.dto.AirportDTO;
import com.crimsonlogic.arilinemanangmentsystem.dao.AirportMapper;
import com.crimsonlogic.arilinemanangmentsystem.exception.RecordNotFoundException;
import com.crimsonlogic.arilinemanangmentsystem.model.Airport;
import com.crimsonlogic.arilinemanangmentsystem.service.AirportService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service responsible for airport service impl business logic.
 * Encapsulates core application rules and data manipulation.
 */
@Service
public class AirportServiceImpl implements AirportService {

    /**
     * The airport mapper.
     */
    private final AirportMapper airportMapper;

    public AirportServiceImpl(AirportMapper airportMapper) {
        this.airportMapper = airportMapper;
    }



    /**
     * Retrieves the all airports.
     * @return List<Airport> the result of the operation
     */
    @Override
    public List<Airport> getAllAirports() {
        return airportMapper.findAllAirport();
    }




    /**
     * Retrieves the airport by code.
     * @param airportCode the airport code
     * @return Airport the result of the operation
     */
    @Override
    public Airport getAirportByCode(String airportCode) {
        Airport airport =  airportMapper.findById(airportCode);

        if(airport == null ){
           throw  new RecordNotFoundException("Airport not found with code: " + airportCode);
        }
        return  airport;
    }

    /**
     * Retrieves the all airports dto.
     * @return List<AirportDTO> the result of the operation
     */
    @Override
    public List<AirportDTO> getAllAirportsDTO() {

        return getAllAirports()
                .stream()
                .map(airport -> new AirportDTO(
                        airport.getAirportCode(),
                        airport.getAirportName(),
                        airport.getCity()
                ))
                .collect(Collectors.toList());
    }

    // =========================================================
    // NEW - GET AIRPORT BY CODE AS DTO
    // Uses existing getAirportByCode()
    // =========================================================

    /**
     * Retrieves the airport by code dto.
     * @param airportCode the airport code
     * @return AirportDTO the result of the operation
     */
    @Override
    public AirportDTO getAirportByCodeDTO(String airportCode) {

        Airport airport = getAirportByCode(airportCode);

        return new AirportDTO(
                airport.getAirportCode(),
                airport.getAirportName(),
                airport.getCity()
        );
    }
    
    /**
     * Creates or saves add airport.
     * @param airportDTO the airport dto
     * @return AirportDTO the result of the operation
     */
    @Override
    public AirportDTO addAirport(AirportDTO airportDTO) {
        if (airportMapper.findById(airportDTO.getAirportCode()) != null) {
            throw new com.crimsonlogic.arilinemanangmentsystem.exception.CustomException("Airport with code " + airportDTO.getAirportCode() + " already exists.", org.springframework.http.HttpStatus.CONFLICT);
        }
        
        Airport newAirport = new Airport();
        newAirport.setAirportCode(airportDTO.getAirportCode());
        newAirport.setAirportName(airportDTO.getAirportName());
        newAirport.setCity(airportDTO.getCity());
        
        Airport saved = airportMapper.save(newAirport);
        
        return new AirportDTO(
                saved.getAirportCode(),
                saved.getAirportName(),
                saved.getCity()
        );
    }
}