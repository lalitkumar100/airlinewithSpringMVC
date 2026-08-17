package com.crimsonlogic.arilinemanangmentsystem.model;

import com.crimsonlogic.arilinemanangmentsystem.enumrator.BookingStatus;
import com.crimsonlogic.arilinemanangmentsystem.enumrator.SeatClass;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Entity representing a Booking in the system.
 *
 * @author System Architect
 * @version 1.0
 */
public class Booking implements Comparable<Booking> {

    private String bookingId;

    private ArrayList<Passenger> passengers = new ArrayList<>();

    private Flight flightBooked;

    private LocalDateTime bookingDateTime;

    private BookingStatus bookingStatus;

    private ArrayList<Ticket> tickets = new ArrayList<>();

    private SeatClass seatClass;

    private User userbooked;



    private double amount;

    private Payment payment;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;
    private boolean deleted;


    // Constructors
   public Booking(){

    }

    // toString()
    @Override
    /**
     * Executes the toString operation.
     */
    public String toString() {

        return String.format("""
        +------------------------------------------------------------------------------------------------+
        | %-20s | %-60s |
        +------------------------------------------------------------------------------------------------+
        | %-20s | %-60s |
        | %-20s | %-60s |
        | %-20s | %-60d |
        | %-20s | %-60s |
        | %-20s | %-60.2f |
        | %-20s | %-60s |
        | %-20s | %-60s |
        +------------------------------------------------------------------------------------------------+
        """,
                "Field","Value",
                "Booking ID",bookingId,
                "Flight",flightBooked==null?"-":flightBooked.getFlightCode(),
                "Passengers",passengers.size(),
                "Seat Class",seatClass,
                "Amount",amount,
                "Status",bookingStatus,
                "Booked On",bookingDateTime
        );
    }

    // printHeader()
    /**
     * Executes the printHeader operation.
     */
    public static void printHeader() {

        System.out.printf(
                "%-12s %-12s %-12s %-10s %-12s %-12s %-20s%n",
                "Booking ID",
                "Flight",
                "Passengers",
                "Seat",
                "Amount",
                "Status",
                "Booked On"
        );

        System.out.println("-----------------------------------------------------------------------------------------------------------");
    }

    // toRow()
    /**
     * Executes the toRow operation.
     */
    public String toRow() {

        return String.format(
                "%-12s %-12s %-12d %-10s %-12.2f %-12s %-20s",
                bookingId,
                flightBooked == null ? "-" : flightBooked.getFlightCode(),
                passengers.size(),
                seatClass,
                amount,
                bookingStatus,
                bookingDateTime
        );
    }

    // displayPassengers()
    /**
     * Executes the displayPassengers operation.
     */
    public void displayPassengers() {

        if(passengers.isEmpty()){

            System.out.println("No Passengers Found.");
            return;
        }

        Passenger.printHeader();

        for(Passenger passenger : passengers){

            System.out.println(passenger.toRow());
        }
    }

    // displayAllTickets()
    /**
     * Executes the displayAllTickets operation.
     */
    public void displayAllTickets() {

        if(tickets.isEmpty()){

            System.out.println("No Tickets Found.");
            return;
        }

        Ticket.printHeader();

        for(Ticket ticket : tickets){

            System.out.println(ticket.toRow());
        }
    }

    // cancel()
    /**
     * Executes the cancel operation.
     */
    public void cancel(){

        bookingStatus = BookingStatus.CANCELLED;

        // TODO
        // Refund Payment
        // Cancel Tickets
        // Update Flight Seat Availability
    }

    // checkIn()
    /**
     * Executes the checkIn operation.
     */
    public void checkIn(){

        bookingStatus = BookingStatus.CHECKED_IN;
    }

    @Override
    /**
     * Executes the compareTo operation.
     */
    public int compareTo(Booking o) {
        return 0;
    }

    void genrateOnBoardingPass(){

    }

    void gernateBooking(User user ,ArrayList<Passenger> passengers){
        //loyalaccount update
        //booking creation ok
    }

    /**

     * Retrieves the bookingid.

     */

    public String getBookingId() {
        return bookingId;
    }

    /**

     * Updates the bookingid.

     */

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    /**

     * Retrieves the passengers.

     */

    public ArrayList<Passenger> getPassengers() {
        return passengers;
    }

    /**

     * Updates the passengers.

     */

    public void setPassengers(ArrayList<Passenger> passengers) {
        this.passengers = passengers;
    }

    /**

     * Retrieves the flightbooked.

     */

    public Flight getFlightBooked() {
        return flightBooked;
    }

    /**

     * Updates the flightbooked.

     */

    public void setFlightBooked(Flight flightBooked) {
        this.flightBooked = flightBooked;
    }

    /**

     * Retrieves the bookingdatetime.

     */

    public LocalDateTime getBookingDateTime() {
        return bookingDateTime;
    }

    /**

     * Updates the bookingdatetime.

     */

    public void setBookingDateTime(LocalDateTime bookingDateTime) {
        this.bookingDateTime = bookingDateTime;
    }

    /**

     * Retrieves the bookingstatus.

     */

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    /**

     * Updates the bookingstatus.

     */

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    /**

     * Retrieves the tickets.

     */

    public ArrayList<Ticket> getTickets() {
        return tickets;
    }

    /**

     * Updates the tickets.

     */

    public void setTickets(ArrayList<Ticket> tickets) {
        this.tickets = tickets;
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

     * Retrieves the amount.

     */

    public double getAmount() {
        return amount;
    }

    /**

     * Updates the amount.

     */

    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**

     * Retrieves the payment.

     */

    public Payment getPayment() {
        return payment;
    }

    /**

     * Updates the payment.

     */

    public void setPayment(Payment payment) {
        this.payment = payment;
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

    public User getUserbooked() {
        return userbooked;
    }

    public void setUserbooked(User userbooked) {
        this.userbooked = userbooked;
    }
}