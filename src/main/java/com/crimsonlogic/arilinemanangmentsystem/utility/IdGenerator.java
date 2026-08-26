package com.crimsonlogic.arilinemanangmentsystem.utility;

import java.util.concurrent.ThreadLocalRandom;

public final class IdGenerator {



    private IdGenerator() {
    }

    /**
     * Action for generate6DigitCode.
     * @return String output
     */
    private static String generate6DigitCode() {

        long id = ThreadLocalRandom.current().nextLong(1, 1_000_000);

        return String.format("%06d", id);
    }

    /**
     * Action for generatePassengerId.
     * @return String output
     */
    public static String generatePassengerId() {
        return "PAS" + generate6DigitCode();
    }

    /**
     * Action for generateFlightId.
     * @return String output
     */
    public static String generateFlightId() {
        return "FLT" + generate6DigitCode();
    }


    /**
     * Action for generateAircraftId.
     * @return String output
     */
    public static String generateAircraftId() {
        return "AIR" + generate6DigitCode();
    }

    /**
     * Action for generateBookingId.
     * @return String output
     */
    public static String generateBookingId() {
        return "BKG" + generate6DigitCode();
    }

    /**
     * Action for generatePaymentId.
     * @return String output
     */
    public static String generatePaymentId() {
        return "PAY" + generate6DigitCode();
    }
    /**
     * Action for generateRefundId.
     * @return String output
     */
    public static String generateRefundId() {
        return "REF" + generate6DigitCode();
    }

    /**
     * Action for generateTicketId.
     * @return String output
     */
    public static String generateTicketId() {
        return "TKT" + generate6DigitCode();
    }

    /**
     * Action for generateLoyaltyId.
     * @return String output
     */
    public static String generateLoyaltyId() {
        return "LOY" + generate6DigitCode();
    }

    /**
     * Action for generateUserId.
     * @return String output
     */
    public static String generateUserId(){
        return "USR"+generate6DigitCode();
    }

    /**
     * Action for generateTransactionId.
     * @return String output
     */
    public static String generateTransactionId(){
        return "TSA"+generate6DigitCode();
    }

    /**
     * Action for generateWalletId.
     * @return String output
     */
    public static String generateWalletId(){
        return "WLT"+generate6DigitCode();
    }
}