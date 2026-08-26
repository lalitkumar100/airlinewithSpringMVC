package com.crimsonlogic.arilinemanangmentsystem.dto;

/**
 * Data Transfer Object for airline revenue dto.
 * Used to transfer data between the client and the server.
 */
public class AirlineRevenueDTO {

    private long totalBookings;
    /**
     * The total cancelled bookings.
     */
    private long totalCancelledBookings;
    private double totalBookingAmount;
    private double totalRefundAmount;
    /**
     * The total revenue.
     */
    private double totalRevenue;

    /**
     * Default constructor for AirlineRevenueDTO.
     */
    public AirlineRevenueDTO() {
    }

    /**
     * Parameterized constructor for AirlineRevenueDTO.
     * @param totalBookings The total number of bookings
     * @param totalCancelledBookings The total number of cancelled bookings
     * @param totalBookingAmount The total booking amount
     * @param totalRefundAmount The total refund amount
     * @param totalRevenue The net total revenue
     */
    public AirlineRevenueDTO(long totalBookings, long totalCancelledBookings, double totalBookingAmount, double totalRefundAmount, double totalRevenue) {
        this.totalBookings = totalBookings;
        this.totalCancelledBookings = totalCancelledBookings;
        this.totalBookingAmount = totalBookingAmount;
        this.totalRefundAmount = totalRefundAmount;
        this.totalRevenue = totalRevenue;
    }

    /**
     * Gets total bookings.
     * @return the total bookings
     */
    public long getTotalBookings() {
        return totalBookings;
    }

    /**
     * Sets total bookings.
     * @param totalBookings the total bookings
     */
    public void setTotalBookings(long totalBookings) {
        this.totalBookings = totalBookings;
    }

    /**
     * Gets total cancelled bookings.
     * @return the total cancelled bookings
     */
    public long getTotalCancelledBookings() {
        return totalCancelledBookings;
    }

    /**
     * Sets total cancelled bookings.
     * @param totalCancelledBookings the total cancelled bookings
     */
    public void setTotalCancelledBookings(long totalCancelledBookings) {
        this.totalCancelledBookings = totalCancelledBookings;
    }

    /**
     * Gets total booking amount.
     * @return the total booking amount
     */
    public double getTotalBookingAmount() {
        return totalBookingAmount;
    }

    /**
     * Sets total booking amount.
     * @param totalBookingAmount the total booking amount
     */
    public void setTotalBookingAmount(double totalBookingAmount) {
        this.totalBookingAmount = totalBookingAmount;
    }

    /**
     * Gets total refund amount.
     * @return the total refund amount
     */
    public double getTotalRefundAmount() {
        return totalRefundAmount;
    }

    /**
     * Sets total refund amount.
     * @param totalRefundAmount the total refund amount
     */
    public void setTotalRefundAmount(double totalRefundAmount) {
        this.totalRefundAmount = totalRefundAmount;
    }

    /**
     * Gets total revenue.
     * @return the total revenue
     */
    public double getTotalRevenue() {
        return totalRevenue;
    }

    /**
     * Sets total revenue.
     * @param totalRevenue the total revenue
     */
    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}
