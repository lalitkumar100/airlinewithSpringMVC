package com.crimsonlogic.arilinemanangmentsystem.dao;

import com.crimsonlogic.arilinemanangmentsystem.model.Airport;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

@org.springframework.stereotype.Repository
public interface AirportMapper extends Repository<Airport, String> {

    /**
     * Action for findAllAirport.
     * @return List<Airport> output
     */
    @Query("SELECT a FROM Airport a WHERE a.deleted = false")
    List<Airport> findAllAirport();

    /**
     * Action for findById.
     * @param airportCode input parameter
     * @return Airport output
     */
    @Query("SELECT a FROM Airport a WHERE a.airportCode = ?1 AND a.deleted = false")
    Airport findById(String airportCode);
    
    /**
     * Action for save.
     * @param airport input parameter
     * @return Airport output
     */
    Airport save(Airport airport);
}