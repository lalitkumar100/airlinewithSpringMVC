package com.crimsonlogic.arilinemanangmentsystem.model;

/**
 * Entity representing a Aircraft in the system.
 *
 * @author System Architect
 * @version 1.0
 */
public class Aircraft {

    private String aircraftId;

    private String model;

    private int capacity;

    /**

     * Executes the Aircraft operation.

     */

    public Aircraft() {
    }

    /**

     * Executes the Aircraft operation.

     */

    public Aircraft(String aircraftId, String model, int capacity) {
        this.aircraftId = aircraftId;
        this.model = model;
        this.capacity = capacity;
    }





    /**





     * Executes the printHeader operation.





     */





    public static void printHeader() {

        System.out.printf(
                "%-15s %-35s %-10s%n",
                "Aircraft ID",
                "Model",
                "Capacity"
        );

        System.out.println("---------------------------------------------------------------");
    }

    /**

     * Executes the toRow operation.

     */

    public String toRow() {

        return String.format(
                "%-15s %-35s %-10d",
                aircraftId,
                model,
                capacity
        );
    }

    @Override
    /**
     * Executes the toString operation.
     */
    public String toString() {

        return String.format("""
        +-----------------------------------------------------------------------+
        | %-18s | %-45s |
        +-----------------------------------------------------------------------+
        | %-18s | %-45s |
        | %-18s | %-45s |
        | %-18s | %-45d |
        +-----------------------------------------------------------------------+
        """,
                "Field", "Value",
                "Aircraft ID", aircraftId,
                "Model", model,
                "Capacity", capacity
        );
    }

    /**
     * Displays complete aircraft information.
     */
    /**
     * Executes the displayInfo operation.
     */
    public void displayInfo() {

        System.out.println("\n========== AIRCRAFT DETAILS ==========");
        System.out.println("Aircraft ID : " + aircraftId);
        System.out.println("Model       : " + model);
        System.out.println("Capacity    : " + capacity);
    }

    /**

     * Updates the aircraftid.

     */

    public void setAircraftId(String aircraftId) {
        this.aircraftId = aircraftId;
    }

    /**

     * Retrieves the aircraftid.

     */

    public String getAircraftId() {
        return aircraftId;
    }


    /**


     * Retrieves the model.


     */


    public String getModel() {
        return model;
    }

    /**

     * Updates the model.

     */

    public void setModel(String model) {
        this.model = model;
    }

    /**

     * Retrieves the capacity.

     */

    public int getCapacity() {
        return capacity;
    }

    /**

     * Updates the capacity.

     */

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
