package com.crimsonlogic.arilinemanangmentsystem.repository;

import com.crimsonlogic.arilinemanangmentsystem.model.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirportRepository extends JpaRepository<Airport, String> {
    /**
     * Action for findByDeletedFalse.
     * @return List<Airport> output
     */
    List<Airport> findByDeletedFalse();
    Airport findByAirportCodeAndDeletedFalse(String airportCode);
}
