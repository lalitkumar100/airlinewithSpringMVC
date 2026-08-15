package com.crimsonlogic.arilinemanangmentsystem.dao;

import com.crimsonlogic.arilinemanangmentsystem.model.Aircraft;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AircraftMapper {

    @Select("""
        SELECT
            aircraft_id,
            model,
            capacity,
            created_at,
            updated_at,
            is_deleted
        FROM aircraft
        WHERE is_deleted = 0
        """)
    List<Aircraft> findAllAircraft();


    @Select("""
        SELECT
            aircraft_id,
            model,
            capacity,
            created_at,
            updated_at,
            is_deleted
        FROM aircraft
        WHERE aircraft_id = #{aircraftId}
          AND is_deleted = 0
        """)
    Aircraft findById(String aircraftId);
}
