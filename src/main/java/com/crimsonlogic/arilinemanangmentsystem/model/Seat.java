package com.crimsonlogic.arilinemanangmentsystem.model;

/**
 * Entity representing a Seat in the system.
 *
 * @author System Architect
 * @version 1.0
 */
public class Seat {

    final char   SEAT_A ='A';
    final char   SEAT_B ='B';
    final char   SEAT_C ='C';

    private int seatNo;



    private char SeatType ;
    private boolean available;
    private boolean upgraded;

    /**

     * Executes the Seat operation.

     */

    public Seat(int seatNo, char seatType, boolean available, boolean upgraded) {
        this.seatNo = seatNo;
        this.SeatType = seatType;
        this.available = available;
        this.upgraded = upgraded;
    }
    /**
     * Retrieves the seatno.
     */
    public int getSeatNo() {
        return seatNo;
    }



}