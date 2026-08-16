package com.crimsonlogic.arilinemanangmentsystem.model;

import com.crimsonlogic.arilinemanangmentsystem.enumrator.WalletStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.time.LocalDateTime;

/**
 * Entity representing a Wallet in the system.
 *
 * @author System Architect
 * @version 1.0
 */
public class Wallet {

    private String walletId;

    @JsonBackReference
    private User user;

    private double balance;
    private String currency;
    private WalletStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;
    private boolean deleted;


    @Override
    /**
     * Executes the toString operation.
     */
    public String toString() {

        return String.format("""
        +-------------------------------------------------------------------------------------------------------+
        | %-20s | %-80s |
        +-------------------------------------------------------------------------------------------------------+
        | %-20s | %-80s |
        | %-20s | %-80s |
        | %-20s | %-80.2f |
        | %-20s | %-80s |
        | %-20s | %-80s |
        | %-20s | %-80s |
        | %-20s | %-80s |
        | %-20s | %-80s |
        +-------------------------------------------------------------------------------------------------------+
        """,
                "Field", "Value",
                "Wallet ID", walletId,
                "Owner", user == null ? "N/A" : user.getFirstName() + " " + user.getLastName(),
                "Balance", balance,
                "Currency", currency,
                "Status", status,
                "Created At", createdAt,
                "Updated At", updatedAt,
                "Deleted", deleted
        );
    }

    /**

     * Executes the printHeader operation.

     */

    public static void printHeader() {

        System.out.printf(
                "%-15s %-25s %-12s %-10s %-12s %-22s %-22s%n",
                "Wallet ID",
                "Owner",
                "Balance",
                "Currency",
                "Status",
                "Created At",
                "Updated At"
        );

        System.out.println("-------------------------------------------------------------------------------------------------------------------------");
    }

    /**

     * Executes the toRow operation.

     */

    public String toRow() {

        return String.format(
                "%-15s %-25s %-12.2f %-10s %-12s %-22s %-22s",
                walletId,
                user == null ? "N/A" : user.getFirstName() + " " + user.getLastName(),
                balance,
                currency,
                status,
                createdAt,
                updatedAt
        );
    }

    /**

     * Retrieves the walletid.

     */

    public String getWalletId() {
        return walletId;
    }

    /**

     * Updates the walletid.

     */

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    /**

     * Retrieves the user.

     */

    public User getUser() {
        return user;
    }

    /**

     * Updates the user.

     */

    public void setUser(User user) {
        this.user = user;
    }

    /**

     * Retrieves the balance.

     */

    public double getBalance() {
        return balance;
    }

    /**

     * Updates the balance.

     */

    public void setBalance(double balance) {
        this.balance = balance;
    }

    /**

     * Retrieves the currency.

     */

    public String getCurrency() {
        return currency;
    }

    /**

     * Updates the currency.

     */

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**

     * Retrieves the status.

     */

    public WalletStatus getStatus() {
        return status;
    }

    /**

     * Updates the status.

     */

    public void setStatus(WalletStatus status) {
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