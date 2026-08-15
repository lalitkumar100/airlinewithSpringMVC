package com.crimsonlogic.arilinemanangmentsystem.utility;

import java.util.concurrent.ThreadLocalRandom;

public final class IdGenerator {



    private IdGenerator() {
    }

    private static String generate6DigitCode() {

        long id = ThreadLocalRandom.current().nextLong(1, 1_000_000);

        return String.format("%06d", id);
    }

    public static String generatePassengerId() {
        return "PAS" + generate6DigitCode();
    }

    public static String generateFlightId() {
        return "FLT" + generate6DigitCode();
    }


    public static String generateAircraftId() {
        return "AIR" + generate6DigitCode();
    }

    public static String generateBookingId() {
        return "BKG" + generate6DigitCode();
    }

    public static String generatePaymentId() {
        return "PAY" + generate6DigitCode();
    }
    public static String generateRefundId() {
        return "REF" + generate6DigitCode();
    }

    public static String generateTicketId() {
        return "TKT" + generate6DigitCode();
    }

    public static String generateLoyaltyId() {
        return "LOY" + generate6DigitCode();
    }

    public static String generateUserId(){
        return "USR"+generate6DigitCode();
    }

    public static String generateTransactionId(){
        return "TSA"+generate6DigitCode();
    }

    public static String generateWalletId(){
        return "WLT"+generate6DigitCode();
    }
}