package com.crimsonlogic.arilinemanangmentsystem.model;

import com.crimsonlogic.arilinemanangmentsystem.enumrator.SeatClass;
import com.crimsonlogic.arilinemanangmentsystem.utility.IdGenerator;

import java.time.LocalDateTime;

/**
 * Entity representing a Ticket in the system.
 *
 * @author System Architect
 * @version 1.0
 */
public class Ticket {

    private String ticketId;

    private Booking booking;

    private Passenger passenger;

    private double fare;

    private SeatClass seatClass;

    private String seatNumber;

    // Audit fields
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean deleted;

    /**

     * Executes the Ticket operation.

     */
   public Ticket(){

   }
    public Ticket(Booking booking,
                  Passenger passenger,
                  double fare,
                  SeatClass seatClass,
                  String seatNumber) {

        this.ticketId = IdGenerator.generateTicketId();

        this.booking = booking;
        this.passenger = passenger;
        this.fare = fare;
        this.seatClass = seatClass;
        this.seatNumber = seatNumber;

        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.deleted = false;
    }

    /**

     * Executes the printHeader operation.

     */

    public static void printHeader() {

        System.out.printf(
                "%-15s %-15s %-12s %-18s %-12s %-22s %-10s%n",
                "Ticket ID",
                "Booking",
                "Fare",
                "Seat Class",
                "Seat No",
                "Created At",
                "Deleted"
        );

        System.out.println("----------------------------------------------------------------------------------------------------------------------");
    }


    /**


     * Executes the toRow operation.


     */


    public String toRow() {

        return String.format(
                "%-15s %-15s %-12.2f %-18s %-12s %-22s %-10s",
                ticketId,
                booking == null ? "-" : booking.getBookingId(),
                fare,
                seatClass,
                seatNumber,
                createdAt,
                deleted
        );
    }

    @Override
    /**
     * Executes the toString operation.
     */
    public String toString() {

        return String.format("""
        +--------------------------------------------------------------------------------------+
        | %-20s | %-50s |
        +--------------------------------------------------------------------------------------+
        | %-20s | %-50s |
        | %-20s | %-50s |
        | %-20s | %-50.2f |
        | %-20s | %-50s |
        | %-20s | %-50s |
        | %-20s | %-50s |
        | %-20s | %-50s |
        | %-20s | %-50s |
        +--------------------------------------------------------------------------------------+
        """,
                "Field", "Value",
                "Ticket ID", ticketId,
                "Booking ID", booking == null ? "-" : booking.getBookingId(),
                "Fare", fare,
                "Seat Class", seatClass,
                "Seat Number", seatNumber,
                "Created At", createdAt,
                "Updated At", updatedAt,
                "Deleted", deleted
        );
    }

    /**

     * Retrieves the passenger.

     */

    public Passenger getPassenger() {
        return passenger;
    }

    /**

     * Updates the passenger.

     */

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    /**

     * Retrieves the ticketid.

     */

    public String getTicketId() {
        return ticketId;
    }

    /**

     * Updates the ticketid.

     */

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    /**

     * Retrieves the booking.

     */

    public Booking getBooking() {
        return booking;
    }

    /**

     * Updates the booking.

     */

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    /**

     * Retrieves the fare.

     */

    public double getFare() {
        return fare;
    }

    /**

     * Updates the fare.

     */

    public void setFare(double fare) {
        this.fare = fare;
    }

    /**

     * Retrieves the seatclass.

     */

    public SeatClass getSeatClass() {
        return seatClass;
    }

    /**

     * Updates the seatclass.

     */

    public void setSeatClass(SeatClass seatClass) {
        this.seatClass = seatClass;
    }

    /**

     * Retrieves the seatnumber.

     */

    public String getSeatNumber() {
        return seatNumber;
    }

    /**

     * Updates the seatnumber.

     */

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
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