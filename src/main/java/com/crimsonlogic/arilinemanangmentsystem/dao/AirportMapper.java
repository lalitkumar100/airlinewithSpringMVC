package com.crimsonlogic.arilinemanangmentsystem.dao;

import com.crimsonlogic.arilinemanangmentsystem.model.Airport;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

@org.springframework.stereotype.Repository
public interface AirportMapper extends Repository<Airport, String> {

    @Query("SELECT a FROM Airport a WHERE a.deleted = false")
    List<Airport> findAllAirport();

    @Query("SELECT a FROM Airport a WHERE a.airportCode = ?1 AND a.deleted = false")
    Airport findById(String airportCode);
    
    Airport save(Airport airport);
}