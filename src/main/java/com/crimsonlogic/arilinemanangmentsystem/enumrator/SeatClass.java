package com.crimsonlogic.arilinemanangmentsystem.enumrator;

public enum SeatClass {

    FIRST_CLASS("First Class"),
    BUSINESS_CLASS("Business Class"),
    ECONOMY_CLASS("Economy Class");

    private final String displayName;

    SeatClass(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Action for toString.
     * @return String output
     */
    @Override
    public String toString() {
        return displayName;
    }
}
