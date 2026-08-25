package com.crimsonlogic.arilinemanangmentsystem.model;

import com.crimsonlogic.arilinemanangmentsystem.enumrator.PaymentMethod;
import com.crimsonlogic.arilinemanangmentsystem.enumrator.TransactionStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

/**
 * Entity representing a Transaction in the system.
 *
 * @author System Architect
 * @version 1.0
 */
@Entity
@Table(name = "`transaction`")
public class Transaction {

    @Id
    @Column(name = "transaction_id", length = 20)
    private String transactionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_user_id")
    private User sender;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_user_id")
    private User receiver;

    @Enumerated(EnumType.STRING)
    @Column(name = "from_payment_method", length = 20)
    private PaymentMethod fromPaymentMethod;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "to_payment_method", length = 20)
    private PaymentMethod toPaymentMethod;

    @Column(name = "sender_upi", length = 100)
    private String senderUpi;      // null if Wallet
    
    @Column(name = "receiver_upi", length = 100)
    private String receiverUpi;    // null if Wallet

    @Column(name = "amount")
    private double amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private TransactionStatus status;

    @Column(name = "transaction_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime transactionTime;


    @Override
    /**
     * Executes the toString operation.
     */
    public String toString() {
        return String.format("""
        +--------------------------------------------------------------------------------------------------------------+
        | %-25s | %-60s |
        +--------------------------------------------------------------------------------------------------------------+
        | %-25s | %-60s |
        | %-25s | %-60s |
        | %-25s | %-60s |
        | %-25s | %-60s |
        | %-25s | %-60s |
        | %-25s | %-60s |
        | %-25s | %-60s |
        | %-25s | %-60.2f |
        | %-25s | %-60s |
        | %-25s | %-60s |
        +--------------------------------------------------------------------------------------------------------------+
        """,
                "Field", "Value",
                "Transaction ID", transactionId,
                "Sender", sender == null ? "N/A" : sender.getFirstName() + " " + sender.getLastName(),
                "Receiver", receiver == null ? "N/A" : receiver.getFirstName() + " " + receiver.getLastName(),
                "From Payment", fromPaymentMethod,
                "To Payment", toPaymentMethod,
                "Sender UPI", senderUpi == null ? "-" : senderUpi,
                "Receiver UPI", receiverUpi == null ? "-" : receiverUpi,
                "Amount", amount,
                "Status", status,
                "Transaction Time", transactionTime
        );
    }

    /**

     * Executes the printHeader operation.

     */

    public static void printHeader() {

        System.out.printf(
                "%-8s %-20s %-20s %-12s %-12s %-20s %-20s %-12s %-12s %-22s%n",
                "Txn ID",
                "Sender",
                "Receiver",
                "From",
                "To",
                "Sender UPI",
                "Receiver UPI",
                "Amount",
                "Status",
                "Time"
        );

        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    /**

     * Executes the toRow operation.

     */

    public String toRow() {

        return String.format(
                "%-8s %-20s %-20s %-12s %-12s %-20s %-20s %-12.2f %-12s %-22s",
                transactionId,
                sender == null ? "N/A" : sender.getFirstName() + " " + sender.getLastName(),
                receiver == null ? "N/A" : receiver.getFirstName() + " " + receiver.getLastName(),
                fromPaymentMethod,
                toPaymentMethod,
                senderUpi == null ? "-" : senderUpi,
                receiverUpi == null ? "-" : receiverUpi,
                amount,
                status,
                transactionTime
        );
    }


    /**


     * Retrieves the transactionid.


     */


    public String getTransactionId() {
        return transactionId;
    }

    /**

     * Updates the transactionid.

     */

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    /**

     * Retrieves the sender.

     */

    public User getSender() {
        return sender;
    }

    /**

     * Updates the sender.

     */

    public void setSender(User sender) {
        this.sender = sender;
    }

    /**

     * Retrieves the receiver.

     */

    public User getReceiver() {
        return receiver;
    }

    /**

     * Updates the receiver.

     */

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    /**

     * Retrieves the frompaymentmethod.

     */

    public PaymentMethod getFromPaymentMethod() {
        return fromPaymentMethod;
    }

    /**

     * Updates the frompaymentmethod.

     */

    public void setFromPaymentMethod(PaymentMethod fromPaymentMethod) {
        this.fromPaymentMethod = fromPaymentMethod;
    }

    /**

     * Retrieves the topaymentmethod.

     */

    public PaymentMethod getToPaymentMethod() {
        return toPaymentMethod;
    }

    /**

     * Updates the topaymentmethod.

     */

    public void setToPaymentMethod(PaymentMethod toPaymentMethod) {
        this.toPaymentMethod = toPaymentMethod;
    }

    /**

     * Retrieves the senderupi.

     */

    public String getSenderUpi() {
        return senderUpi;
    }

    /**

     * Updates the senderupi.

     */

    public void setSenderUpi(String senderUpi) {
        this.senderUpi = senderUpi;
    }

    /**

     * Retrieves the receiverupi.

     */

    public String getReceiverUpi() {
        return receiverUpi;
    }

    /**

     * Updates the receiverupi.

     */

    public void setReceiverUpi(String receiverUpi) {
        this.receiverUpi = receiverUpi;
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

     * Retrieves the status.

     */

    public TransactionStatus getStatus() {
        return status;
    }

    /**

     * Updates the status.

     */

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    /**

     * Retrieves the transactiontime.

     */

    public LocalDateTime getTransactionTime() {
        return transactionTime;
    }

    /**

     * Updates the transactiontime.

     */

    public void setTransactionTime(LocalDateTime transactionTime) {
        this.transactionTime = transactionTime;
    }



}
