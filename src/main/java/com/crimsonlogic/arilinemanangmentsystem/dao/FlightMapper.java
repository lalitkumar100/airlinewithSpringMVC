package com.crimsonlogic.arilinemanangmentsystem.dao;

import com.crimsonlogic.arilinemanangmentsystem.model.Flight;
import com.crimsonlogic.arilinemanangmentsystem.enumrator.FlightStatus;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface FlightMapper {

    @Results(id = "FlightResultMap", value = {
            @Result(property = "flightId", column = "flight_id"),
            @Result(property = "flightCode", column = "flight_code"),
            @Result(property = "source", column = "source_airport",
                    one = @One(select = "com.crimsonlogic.arilinemanangmentsystem.dao.AirportMapper.findById")),
            @Result(property = "destination", column = "destination_airport",
                    one = @One(select = "com.crimsonlogic.arilinemanangmentsystem.dao.AirportMapper.findById")),
            @Result(property = "departureDateTime", column = "departure_time"),
            @Result(property = "arrivalDateTime", column = "arrival_time"),
            @Result(property = "aircraft", column = "aircraft_id",
                    one = @One(select = "com.crimsonlogic.arilinemanangmentsystem.dao.AircraftMapper.findById")),
            @Result(property = "baseFare", column = "base_fare"),
            @Result(property = "status", column = "status", javaType = com.crimsonlogic.arilinemanangmentsystem.enumrator.FlightStatus.class, typeHandler = org.apache.ibatis.type.EnumTypeHandler.class),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "updatedAt", column = "updated_at"),
            @Result(property = "deleted", column = "is_deleted")
    })
    @Select("SELECT flight_id, flight_code, source_airport, destination_airport, departure_time, arrival_time, aircraft_id, base_fare, status, created_at, updated_at, is_deleted FROM flight WHERE is_deleted = 0")
    List<Flight> findAllFlights();


    @ResultMap("FlightResultMap")
    @Select("SELECT flight_id, flight_code, source_airport, destination_airport, departure_time, arrival_time, aircraft_id, base_fare, status, created_at, updated_at, is_deleted FROM flight WHERE flight_id = #{flightId} AND is_deleted = 0")
    Flight findById(@Param("flightId") String flightId);

    @Update("UPDATE flight SET departure_time = #{departureDateTime}, arrival_time = #{arrivalDateTime}, aircraft_id = #{aircraftId}, updated_at = NOW() WHERE flight_id = #{flightId}")
    int updateFlightSchedule(@Param("flightId") String flightId, @Param("departureDateTime") LocalDateTime departureDateTime, @Param("arrivalDateTime") LocalDateTime arrivalDateTime, @Param("aircraftId") String aircraftId);

    @Update("UPDATE flight SET status = #{status, typeHandler=org.apache.ibatis.type.EnumTypeHandler}, updated_at = NOW() WHERE flight_id = #{flightId}")
    int updateFlightStatus(@Param("flightId") String flightId, @Param("status") FlightStatus status);


    @Insert("INSERT INTO flight (flight_id, flight_code, source_airport, destination_airport, departure_time, arrival_time, aircraft_id, base_fare, status, created_at, updated_at, is_deleted) " +
            "VALUES (#{flightId}, #{flightCode}, #{source.airportCode}, #{destination.airportCode}, #{departureDateTime}, #{arrivalDateTime}, #{aircraft.aircraftId}, #{baseFare}, #{status, typeHandler=org.apache.ibatis.type.EnumTypeHandler}, NOW(), NOW(), 0)")
    int insertFlight(Flight flight);


    @ResultMap("FlightResultMap")
    @Select("SELECT flight_id, flight_code, source_airport, destination_airport, departure_time, arrival_time, aircraft_id, base_fare, status, created_at, updated_at, is_deleted " +
            "FROM flight " +
            "WHERE source_airport = #{sourceAirport} " +
            "AND destination_airport = #{destinationAirport} " +
            "AND DATE(departure_time) = #{departureDate} " +
            "AND is_deleted = 0")
    List<Flight> searchFlightsByDate(@Param("sourceAirport") String sourceAirport, 
                                     @Param("destinationAirport") String destinationAirport, 
                                     @Param("departureDate") java.time.LocalDate departureDate);
    
}