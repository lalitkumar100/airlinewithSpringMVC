package com.crimsonlogic.arilinemanangmentsystem.dao;

import com.crimsonlogic.arilinemanangmentsystem.model.Flight;
import com.crimsonlogic.arilinemanangmentsystem.enumrator.FlightStatus;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@org.springframework.stereotype.Repository
public interface FlightMapper extends org.springframework.data.repository.Repository<Flight, String> {
    /**
     * Action for save.
     * @param entity input parameter
     * @return Flight output
     */
    Flight save(Flight entity);


    /**
     * Action for findAllFlights.
     * @return List<Flight> output
     */
    @Query("SELECT f FROM Flight f WHERE f.deleted = false")
    List<Flight> findAllFlights();

    /**
     * Action for findById.
     * @param flightId input parameter
     * @return Flight output
     */
    @Query("SELECT f FROM Flight f WHERE f.flightId = :flightId AND f.deleted = false")
    Flight findById(@Param("flightId") String flightId);

    @Transactional
    @Modifying
    @Query("UPDATE Flight f SET f.departureDateTime = :departureDateTime, f.arrivalDateTime = :arrivalDateTime, f.aircraft.aircraftId = :aircraftId WHERE f.flightId = :flightId")
    int updateFlightSchedule(
            @Param("flightId") String flightId,
            @Param("departureDateTime") LocalDateTime departureDateTime,
            @Param("arrivalDateTime") LocalDateTime arrivalDateTime,
            @Param("aircraftId") String aircraftId
    );

    @Transactional
    @Modifying
    @Query("UPDATE Flight f SET f.status = :status WHERE f.flightId = :flightId")
    int updateFlightStatus(
            @Param("flightId") String flightId,
            @Param("status") FlightStatus status
    );

    default int insertFlight(Flight flight) {
        save(flight);
        return 1;
    }

    @Query("SELECT f FROM Flight f WHERE f.source.airportCode = :sourceAirport " +
           "AND f.destination.airportCode = :destinationAirport " +
           "AND DATE(f.departureDateTime) = DATE(:departureDate) " +
           "AND f.deleted = false")
    List<Flight> searchFlightsByDate(
            @Param("sourceAirport") String sourceAirport,
            @Param("destinationAirport") String destinationAirport,
            @Param("departureDate") java.time.LocalDate departureDate
    );
}
