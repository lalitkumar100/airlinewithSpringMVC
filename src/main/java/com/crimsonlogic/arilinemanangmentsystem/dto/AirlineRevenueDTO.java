package com.crimsonlogic.arilinemanangmentsystem.dto;

public class AirlineRevenueDTO {

    private long totalBookings;
    private long totalCancelledBookings;
    private double totalBookingAmount;
    private double totalRefundAmount;
    private double totalRevenue;

    public AirlineRevenueDTO() {
    }

    public AirlineRevenueDTO(long totalBookings, long totalCancelledBookings, double totalBookingAmount, double totalRefundAmount, double totalRevenue) {
        this.totalBookings = totalBookings;
        this.totalCancelledBookings = totalCancelledBookings;
        this.totalBookingAmount = totalBookingAmount;
        this.totalRefundAmount = totalRefundAmount;
        this.totalRevenue = totalRevenue;
    }

    public long getTotalBookings() {
        return totalBookings;
    }

    public void setTotalBookings(long totalBookings) {
        this.totalBookings = totalBookings;
    }

    public long getTotalCancelledBookings() {
        return totalCancelledBookings;
    }

    public void setTotalCancelledBookings(long totalCancelledBookings) {
        this.totalCancelledBookings = totalCancelledBookings;
    }

    public double getTotalBookingAmount() {
        return totalBookingAmount;
    }

    public void setTotalBookingAmount(double totalBookingAmount) {
        this.totalBookingAmount = totalBookingAmount;
    }

    public double getTotalRefundAmount() {
        return totalRefundAmount;
    }

    public void setTotalRefundAmount(double totalRefundAmount) {
        this.totalRefundAmount = totalRefundAmount;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}
