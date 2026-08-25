package com.crimsonlogic.arilinemanangmentsystem.dao;

import com.crimsonlogic.arilinemanangmentsystem.model.Aircraft;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

@org.springframework.stereotype.Repository
public interface AircraftMapper extends Repository<Aircraft, String> {

    @Query("SELECT a FROM Aircraft a WHERE a.deleted = false")
    List<Aircraft> findAllAircraft();

    @Query("SELECT a FROM Aircraft a WHERE a.aircraftId = ?1 AND a.deleted = false")
    Aircraft findById(String aircraftId);
}
