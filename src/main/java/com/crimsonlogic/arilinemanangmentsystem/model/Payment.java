package com.crimsonlogic.arilinemanangmentsystem.model;

import com.crimsonlogic.arilinemanangmentsystem.utility.IdGenerator;

/**
 * Entity representing a Payment in the system.
 *
 * @author System Architect
 * @version 1.0
 */
public class Payment {

    private String paymentId;

    private double amount;

    private boolean paid;

    private Booking booking;

    private Transaction transaction;

    /**

     * Executes the Payment operation.

     */

    public Payment(double amount, boolean paid, Booking booking, Transaction transaction) {

        this.paymentId = IdGenerator.generatePaymentId();
        this.amount = amount;
        this.paid = paid;
        this.booking=booking;
         this.transaction =transaction;
    }

    /**

     * Executes the printHeader operation.

     */

    public static void printHeader() {

        System.out.printf(
                "%-12s %-12s %-10s %-15s %-15s%n",
                "Payment ID",
                "Amount",
                "Paid",
                "Booking ID",
                "Transaction"
        );

        System.out.println("--------------------------------------------------------------------------");
    }

    /**

     * Executes the toRow operation.

     */

    public String toRow() {

        return String.format(
                "%-12s %-12.2f %-10s %-15s %-15s",
                paymentId,
                amount,
                paid ? "YES" : "NO",
                booking == null ? "-" : booking.getBookingId(),
                transaction == null ? "-" : transaction.getTransactionId()
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
        | %-20s | %-50.2f |
        | %-20s | %-50s |
        | %-20s | %-50s |
        | %-20s | %-50s |
        +--------------------------------------------------------------------------------------+
        """,
                "Field","Value",
                "Payment ID",paymentId,
                "Amount",amount,
                "Paid",paid,
                "Booking ID",booking == null ? "-" : booking.getBookingId(),
                "Transaction",transaction == null ? "-" : transaction.getTransactionId()
        );
    }

    /**

     * Retrieves the paymentid.

     */

    public String getPaymentId() {
        return paymentId;
    }

    /**

     * Updates the paymentid.

     */

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
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

     * Executes the isPaid operation.

     */

    public boolean isPaid() {
        return paid;
    }

    /**

     * Updates the paid.

     */

    public void setPaid(boolean paid) {
        this.paid = paid;
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

     * Retrieves the transaction.

     */

    public Transaction getTransaction() {
        return transaction;
    }

    /**

     * Updates the transaction.

     */

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}