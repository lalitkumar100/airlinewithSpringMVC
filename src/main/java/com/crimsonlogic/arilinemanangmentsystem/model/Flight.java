package com.crimsonlogic.arilinemanangmentsystem.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import com.crimsonlogic.arilinemanangmentsystem.enumrator.FlightStatus;
import com.crimsonlogic.arilinemanangmentsystem.enumrator.SeatClass;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Entity representing a Flight in the system.
 *
 * @author System Architect
 * @version 1.0
 */
public class Flight {

    private String flightId;

    // Generated automatically
    private String flightCode;

    private Airport source;
    private Airport destination;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime departureDateTime;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime arrivalDateTime;

    private Aircraft aircraft;


    @JsonIgnore
    private final ArrayList<Booking> bookings = new ArrayList<>();


    @JsonIgnore
    private final ArrayList<Ticket> tickets = new ArrayList<>();

    private double baseFare;

    private FlightStatus status;

    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;
    
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



        /**



         * Executes the displayAllTickets operation.



         */



        public void displayAllTickets() {

            if (tickets.isEmpty()) {
                System.out.println("\nNo Tickets Found.");
                return;
            }

            Ticket.printHeader();

            for (Ticket ticket : tickets) {
                System.out.println(ticket.toRow());
            }
        }


    /**


     * Executes the cancelBooking operation.


     */


    public void cancelBooking(Booking booking) {

        bookings.remove(booking);
    }

    /**

     * Retrieves the nextwaitingbooking.

     */

    public Booking getNextWaitingBooking() {

        // TODO:
        // Return the first booking whose status is WAITLISTED.

        return null;
    }

    /**

     * Executes the cancelFlight operation.

     */

    public void cancelFlight() {

        this.status = FlightStatus.CANCELLED;

        // TODO:
        // 1. Cancel all bookings
        // 2. Cancel all tickets
        // 3. Notify passengers
        // 4. Process refunds
    }

    /**

     * Executes the changeStatus operation.

     */

    public void changeStatus(FlightStatus status) {

        this.status = status;
    }

    /**

     * Executes the addTicket operation.

     */

    public void addTicket(Ticket ticket) {

        if (ticket != null) {
            tickets.add(ticket);
        }
    }

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

    public ArrayList<Booking> getBookings() {
        return bookings;
    }

    /**

     * Retrieves the tickets.

     */

    public ArrayList<Ticket> getTickets() {
        return tickets;
    }

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