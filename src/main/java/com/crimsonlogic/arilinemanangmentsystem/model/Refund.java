package com.crimsonlogic.arilinemanangmentsystem.model;

import com.crimsonlogic.arilinemanangmentsystem.enumrator.RefundStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Entity representing a Refund in the system.
 *
 * @author System Architect
 * @version 1.0
 */
@Entity
@Table(name = "refund")
public class Refund {

    public static final ArrayList<Refund> refundArrayList = new ArrayList<>();

    /**
     * The refund id.
     */
    @Id
    @Column(name = "refund_id", length = 20)
    private String refundId;

    /**
     * The refund time.
     */
    @Column(name = "refund_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime refundTime;

    /**
     * The amount.
     */
    @Column(name = "amount")
    private double amount;

    /**
     * The booking.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id")
    private Booking booking;

    /**
     * The transaction.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;

    /**
     * The status.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private RefundStatus status;

    /**
     * The reason.
     */
    @Column(name = "reason")
    private String reason;

    /**
     * The created at.
     */
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    /**
     * The updated at.
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * The deleted.
     */
    @Column(name = "is_deleted")
    private boolean deleted;


    /**


     * Executes the printHeader operation.


     */


    public static void printHeader() {

        System.out.printf(
                "%-12s %-12s %-15s %-15s %-12s %-20s%n",
                "Refund ID",
                "Amount",
                "Booking",
                "Status",
                "Transaction",
                "Refund Time"
        );

        System.out.println("------------------------------------------------------------------------------------------------");
    }

    /**

     * Executes the toRow operation.

     */

    public String toRow() {

        return String.format(
                "%-12s %-12.2f %-15s %-15s %-12s %-20s",
                refundId,
                amount,
                booking == null ? "-" : booking.getBookingId(),
                status,
                transaction == null ? "-" : transaction.getTransactionId(),
                refundTime
        );
    }

    @Override
    /**
     * Executes the toString operation.
     */
    public String toString() {

        return String.format("""
        +-------------------------------------------------------------------------------------------+
        | %-20s | %-50s |
        +-------------------------------------------------------------------------------------------+
        | %-20s | %-50s |
        | %-20s | %-50.2f|
        | %-20s | %-50s |
        | %-20s | %-50s |
        | %-20s | %-50s |
        | %-20s | %-50s |
        | %-20s | %-50s |
        +-------------------------------------------------------------------------------------------+
        """,
                "Field", "Value",
                "Refund ID", refundId,
                "Amount", amount,
                "Booking ID", booking == null ? "-" : booking.getBookingId(),
                "Transaction ID", transaction == null ? "-" : transaction.getTransactionId(),
                "Status", status,
                "Reason", reason,
                "Refund Time", refundTime
        );
    }

    /**

     * Retrieves the refundid.

     */

    public String getRefundId() {
        return refundId;
    }

    /**

     * Updates the refundid.

     */

    public void setRefundId(String refundId) {
        this.refundId = refundId;
    }

    /**

     * Retrieves the refundtime.

     */

    public LocalDateTime getRefundTime() {
        return refundTime;
    }

    /**

     * Updates the refundtime.

     */

    public void setRefundTime(LocalDateTime refundTime) {
        this.refundTime = refundTime;
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

    /**

     * Retrieves the status.

     */

    public RefundStatus getStatus() {
        return status;
    }

    /**

     * Updates the status.

     */

    public void setStatus(RefundStatus status) {
        this.status = status;
    }

    /**

     * Retrieves the reason.

     */

    public String getReason() {
        return reason;
    }

    /**

     * Updates the reason.

     */

    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * Retrieves the created at.
     * @return LocalDateTime the result of the operation
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the created at.
     * @param createdAt the created at
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Retrieves the updated at.
     * @return LocalDateTime the result of the operation
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Sets the updated at.
     * @param updatedAt the updated at
     */
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * Checks if the object is deleted.
     * @return boolean the result of the operation
     */
    public boolean isDeleted() {
        return deleted;
    }

    /**
     * Sets the deleted.
     * @param deleted the deleted
     */
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}