package com.crimsonlogic.arilinemanangmentsystem.model;

/**
 * Entity representing a Route in the system.
 *
 * @author System Architect
 * @version 1.0
 */
public class Route {

    private final String sourceAirportId;
    /**
     * The destination airport id.
     */
    private final String destinationAirportId;

    /**

     * Executes the Route operation.

     */

    public Route(String sourceAirportId, String destinationAirportId) {
        this.sourceAirportId = sourceAirportId;
        this.destinationAirportId = destinationAirportId;
    }

    @Override
    /**
     * Executes the equals operation.
     */
    public boolean equals(Object obj) {

        if (this == obj)
            return true;

        if (!(obj instanceof Route))
            return false;

        Route other = (Route) obj;

        return sourceAirportId.equals(other.sourceAirportId)
                && destinationAirportId.equals(other.destinationAirportId);
    }

    @Override
    /**
     * Executes the hashCode operation.
     */
    public int hashCode() {
        return java.util.Objects.hash(sourceAirportId, destinationAirportId);
    }

    public static class RevenueReport {

        /**
         * The flight id.
         */
        private String flightId;
        private double totalBookingAmount;
        private double totalRefundAmount;
        /**
         * The net revenue.
         */
        private double netRevenue;

        /**

         * Executes the RevenueReport operation.

         */

        public RevenueReport(String flightId,
                             double totalBookingAmount,
                             double totalRefundAmount) {

            this.flightId = flightId;
            this.totalBookingAmount = totalBookingAmount;
            this.totalRefundAmount = totalRefundAmount;
            this.netRevenue = totalBookingAmount - totalRefundAmount;
        }

        /**

         * Retrieves the flightid.

         */

        public String getFlightId() {
            return flightId;
        }

        /**

         * Retrieves the totalbookingamount.

         */

        public double getTotalBookingAmount() {
            return totalBookingAmount;
        }

        /**

         * Retrieves the totalrefundamount.

         */

        public double getTotalRefundAmount() {
            return totalRefundAmount;
        }

        /**

         * Retrieves the netrevenue.

         */

        public double getNetRevenue() {
            return netRevenue;
        }

        @Override
        /**
         * Executes the toString operation.
         */
        public String toString() {

            return String.format(
                    "%-10s %-15.2f %-15.2f %-15.2f",
                    flightId,
                    totalBookingAmount,
                    totalRefundAmount,
                    netRevenue);
        }

        /**

         * Executes the displayInfo operation.

         */

        public void displayInfo() {

            System.out.println("\n========== REVENUE REPORT ==========");
            System.out.println("Flight ID        : " + flightId);
            System.out.println("Booking Revenue  : " + totalBookingAmount);
            System.out.println("Refund Amount    : " + totalRefundAmount);
            System.out.println("Net Revenue      : " + netRevenue);
        }
    }
}
