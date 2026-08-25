package com.crimsonlogic.arilinemanangmentsystem.model;

import com.crimsonlogic.arilinemanangmentsystem.enumrator.LoyaltyTier;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * Entity representing a LoyaltyAccount in the system.
 *
 * @author System Architect
 * @version 1.0
 */
@Entity
@Table(name = "loyalty_account")
public class LoyaltyAccount {

    @Id
    @Column(name = "loyalty_account_id", length = 20)
    private String loyaltyAccountId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;



    @Column(name = "points")
    private int points;

    @Enumerated(EnumType.STRING)
    @Column(name = "tier", length = 20)
    private LoyaltyTier tier;

    @Column(name = "created_at", updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;
    
    @Column(name = "is_deleted")
    private boolean deleted;


    /**


     * Executes the LoyaltyAccount operation.


     */


    public LoyaltyAccount() {
        this.points = 0;
        this.tier = LoyaltyTier.SILVER;
    }

    public String getLoyaltyAccountId() {
        return loyaltyAccountId;
    }

    public void setLoyaltyAccountId(String loyaltyAccountId) {
        this.loyaltyAccountId = loyaltyAccountId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    /**

     * Retrieves the points.

     */

    public int getPoints() {
        return points;
    }

    /**

     * Updates the points.

     */

    public void setPoints(int points) {
        this.points = points;
    }

    /**

     * Retrieves the tier.

     */

    public LoyaltyTier getTier() {
        return tier;
    }

    /**

     * Updates the tier.

     */

    public void setTier(LoyaltyTier tier) {
        this.tier = tier;
    }

    /**
     * Updates loyalty points and membership tier.
     *
     * @param seatType Seat type (A, B or C)
     * @param isBooked true for booking, false for cancellation
     */
    /**
     * Executes the update operation.
     */
    public void update(String seatType, boolean isBooked) {

        int earnedPoints = 0;

        switch (seatType.toUpperCase()) {

            case "A":
                earnedPoints = 30;
                break;

            case "B":
                earnedPoints = 20;
                break;

            case "C":
                earnedPoints = 10;
                break;

            default:
                System.out.println("Invalid Seat Type.");
                return;
        }

        if (isBooked) {
            points += earnedPoints;
        } else {
            points -= earnedPoints;

            if (points < 0) {
                points = 0;
            }
        }

        if (points >= 250) {
            tier = LoyaltyTier.DIAMOND;
        } else if (points >= 100) {
            tier = LoyaltyTier.GOLD;
        } else {
            tier = LoyaltyTier.SILVER;
        }
    }

    /**

     * Executes the printHeader operation.

     */

    public static void printHeader() {

        System.out.printf(
                "%-12s %-10s%n",
                "Tier",
                "Points"
        );

        System.out.println("------------------------------");
    }

    /**

     * Executes the toRow operation.

     */

    public String toRow() {

        return String.format(
                "%-12s %-10d",
                tier,
                points
        );
    }

    @Override
    /**
     * Executes the toString operation.
     */
    public String toString() {

        return String.format("""
        +-------------------------------------------------------+
        | %-15s | %-25s |
        +-------------------------------------------------------+
        | %-15s | %-25s |
        | %-15s | %-25d |
        +-------------------------------------------------------+
        """,
                "Field", "Value",
                "Tier", tier,
                "Points", points
        );
    }

    /**
     * Displays loyalty account information.
     */
    /**
     * Executes the displayInfo operation.
     */
    public void displayInfo() {

        System.out.println("\n========== LOYALTY ACCOUNT ==========");
        System.out.println("Tier   : " + tier);
        System.out.println("Points : " + points);
    }



}