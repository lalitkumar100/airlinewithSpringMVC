package com.crimsonlogic.arilinemanangmentsystem.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import com.crimsonlogic.arilinemanangmentsystem.enumrator.FlightStatus;
import com.crimsonlogic.arilinemanangmentsystem.enumrator.SeatClass;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import com.crimsonlogic.arilinemanangmentsystem.enumrator.FlightStatus;
import com.crimsonlogic.arilinemanangmentsystem.enumrator.SeatClass;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * Entity representing a Flight in the system.
 *
 * @author System Architect
 * @version 1.0
 */
@Entity
@Table(name = "flight")
public class Flight {

    @Id
    @Column(name = "flight_id", length = 20)
    private String flightId;

    @Column(name = "flight_code", length = 20)
    private String flightCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_airport")
    private Airport source;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_airport")
    private Airport destination;

    @Column(name = "departure_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime departureDateTime;
    
    @Column(name = "arrival_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime arrivalDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aircraft_id")
    private Aircraft aircraft;

    @OneToMany(mappedBy = "flightBooked", cascade = CascadeType.ALL)
    @JsonIgnore
    private final List<Booking> bookings = new ArrayList<>();



    @Column(name = "base_fare")
    private double baseFare;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private FlightStatus status;
    
    @Column(name = "created_at", updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;
    
    @Column(name = "is_deleted")
    private boolean deleted;


    /**


     * Executes the generateFlightCode operation.


     */


    public void generateFlightCode() {

        String sourceCode = source.getAirportCode().substring(0, 2).toUpperCase();

        String destinationCode = destination.getAirportCode().substring(1).toUpperCase();

        String aircraftCode =
                aircraft.getAircraftId()
                        .replaceAll("\\D", "");

        if (aircraftCode.length() >= 3) {
            aircraftCode = aircraftCode.substring(aircraftCode.length() - 3);
        }

        this.flightCode = sourceCode + destinationCode + aircraftCode;
    }

    /**

     * Executes the printHeader operation.

     */

    public static void printHeader() {

        System.out.printf(
                "%-12s %-10s %-8s %-8s %-20s %-20s %-10s %-12s%n",
                "Flight ID",
                "Code",
                "From",
                "To",
                "Departure",
                "Arrival",
                "Fare",
                "Status"
        );

        System.out.println("------------------------------------------------------------------------------------------------------------------------------");
    }

    /**

     * Executes the toRow operation.

     */

    public String toRow() {

        return String.format(
                "%-12s %-10s %-8s %-8s %-20s %-20s %-10.2f %-12s",
                flightId,
                flightCode,
                source.getAirportCode(),
                destination.getAirportCode(),
                departureDateTime,
                arrivalDateTime,
                baseFare,
                status
        );
    }

    @Override
    /**
     * Executes the toString operation.
     */
    public String toString() {

        return String.format("""
        +------------------------------------------------------------------------------------------------------+
        | %-22s | %-65s |
        +------------------------------------------------------------------------------------------------------+
        | %-22s | %-65s |
        | %-22s | %-65s |
        | %-22s | %-65s |
        | %-22s | %-65s |
        | %-22s | %-65s |
        | %-22s | %-65s |
        | %-22s | %-65.2f |
        | %-22s | %-65s |
        +------------------------------------------------------------------------------------------------------+
        """,
                "Field", "Value",
                "Flight ID", flightId,
                "Flight Code", flightCode,
                "Source", source.getAirportCode(),
                "Destination", destination.getAirportCode(),
                "Departure", departureDateTime,
                "Arrival", arrivalDateTime,
                "Base Fare", baseFare,
                "Status", status
        );
    }

    /**

     * Executes the displayAllBookings operation.

     */

    public void displayAllBookings() {

        if (bookings.isEmpty()) {
            System.out.println("\nNo Bookings Found.");
            return;
        }

        Booking.printHeader();

        for (Booking booking : bookings) {
            System.out.println(booking.toRow());
        }
    }





    public void cancelBooking(Booking booking) {

        bookings.remove(booking);
    }


    public Booking getNextWaitingBooking() {

        // TODO:
        // Return the first booking whose status is WAITLISTED.

        return null;
    }




    public void changeStatus(FlightStatus status) {

        this.status = status;
    }

    /**

     * Executes the addTicket operation.

     */


    /**

     * Retrieves the totalbookings.

     */

    public int getTotalBookings() {

        return bookings.size();
    }

    public Map<SeatClass, Map<String, Object>> hasAvailableSeats() {
    Map<SeatClass, Map<String, Object>> seatInfoMap = new HashMap<>();
    int totalCapacity = aircraft.getCapacity();

    for (SeatClass sc : SeatClass.values()) {
        int maxCapacity = 0;
        double priceMultiplier = 1.0;

        switch (sc) {
            case FIRST_CLASS:
                maxCapacity = (int) (totalCapacity * 0.20);
                priceMultiplier = 3.0;
                break;

            case BUSINESS_CLASS:
                maxCapacity = (int) (totalCapacity * 0.30);
                priceMultiplier = 2.0;
                break;

            case ECONOMY_CLASS:
                maxCapacity = (int) (totalCapacity * 0.50);
                priceMultiplier = 1.0;
                break;
        }

        long booked = bookings.stream()
                .filter(b -> b.getSeatClass() == sc)
                .count();

        Map<String, Object> info = new HashMap<>();
        info.put("price", baseFare * priceMultiplier);
        info.put("available", maxCapacity - booked);

        seatInfoMap.put(sc, info);
    }

    return seatInfoMap;
}

    /**

     * Executes the AvailableSeats operation.

     */

    public boolean AvailableSeats(SeatClass seatClass, int number) {
        int totalCapacity = aircraft.getCapacity();
        int maxCapacity = 0;

        switch (seatClass) {
            case FIRST_CLASS:
                maxCapacity = (int) (totalCapacity * 0.20);
                break;
            case BUSINESS_CLASS:
                maxCapacity = (int) (totalCapacity * 0.30);
                break;
            case ECONOMY_CLASS:
                maxCapacity = (int) (totalCapacity * 0.50);
                break;
        }

        long booked = bookings.stream().filter(b -> b.getSeatClass() == seatClass).count();

        return (booked + number) <= maxCapacity;
    }

    /**

     * Executes the createFlight operation.

     */

    public  Flight createFlight(){
   return null;
    }

    /**

     * Retrieves the flightid.

     */

    public String getFlightId() {
        return flightId;
    }

    /**

     * Updates the flightid.

     */

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    /**

     * Retrieves the flightcode.

     */

    public String getFlightCode() {
        return flightCode;
    }

    /**

     * Updates the flightcode.

     */

    public void setFlightCode(String flightCode) {
        this.flightCode = flightCode;
    }

    /**

     * Retrieves the source.

     */

    public Airport getSource() {
        return source;
    }



    /**

     * Updates the source.

     */

    public void setSource(Airport source) {
        this.source = source;
    }

    /**

     * Retrieves the destination.

     */

    public Airport getDestination() {
        return destination;
    }

    /**

     * Updates the destination.

     */

    public void setDestination(Airport destination) {
        this.destination = destination;
    }

    /**

     * Retrieves the departuredatetime.

     */

    public LocalDateTime getDepartureDateTime() {
        return departureDateTime;
    }

    /**

     * Updates the departuredatetime.

     */

    public void setDepartureDateTime(LocalDateTime departureDateTime) {
        this.departureDateTime = departureDateTime;
    }

    /**

     * Retrieves the arrivaldatetime.

     */

    public LocalDateTime getArrivalDateTime() {
        return arrivalDateTime;
    }

    /**

     * Updates the arrivaldatetime.

     */

    public void setArrivalDateTime(LocalDateTime arrivalDateTime) {
        this.arrivalDateTime = arrivalDateTime;
    }

    /**

     * Retrieves the aircraft.

     */

    public Aircraft getAircraft() {
        return aircraft;
    }

    /**

     * Updates the aircraft.

     */

    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }

    /**

     * Retrieves the bookings.

     */

    public List<Booking> getBookings() {
        return bookings;
    }

    /**

     * Retrieves the tickets.

     */



    /**

     * Retrieves the basefare.

     */

    public double getBaseFare() {
        return baseFare;
    }

    /**

     * Updates the basefare.

     */

    public void setBaseFare(double baseFare) {
        this.baseFare = baseFare;
    }

    /**

     * Retrieves the status.

     */

    public FlightStatus getStatus() {
        return status;
    }

    /**

     * Updates the status.

     */

    public void setStatus(FlightStatus status) {
        this.status = status;
    }

    /**

     * Retrieves the createdat.

     */

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**

     * Updates the createdat.

     */

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**

     * Retrieves the updatedat.

     */

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**

     * Updates the updatedat.

     */

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**

     * Executes the isDeleted operation.

     */

    public boolean isDeleted() {
        return deleted;
    }

    /**

     * Updates the deleted.

     */

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}