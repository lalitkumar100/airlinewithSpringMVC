package com.crimsonlogic.arilinemanangmentsystem.model;

/**
 * Entity representing a Airport in the system.
 *
 * @author System Architect
 * @version 1.0
 */
public class Airport {

    private String airportCode;
    private String airportName;
    private String city;

    /**

     * Executes the Airport operation.

     */

    public Airport() {
    }

    /**

     * Executes the Airport operation.

     */

    public Airport(String airportCode, String airportName, String city) {
        this.airportCode = airportCode;
        this.airportName = airportName;
        this.city = city;
    }





    /**





     * Executes the printHeader operation.





     */





    public static void printHeader() {

        System.out.printf(
                "%-15s %-35s %-25s%n",
                "Airport Code",
                "Airport Name",
                "City"
        );

        System.out.println("---------------------------------------------------------------------------");
    }

    /**

     * Executes the toRow operation.

     */

    public String toRow() {

        return String.format(
                "%-15s %-35s %-25s",
                airportCode,
                airportName,
                city
        );
    }
    /**
     * Displays complete airport information.
     *
     * @return
     */
    @Override
    /**
     * Executes the toString operation.
     */
    public String toString() {

        return String.format("""
        +-----------------------------------------------------------------------+
        | %-15s | %-45s |
        +-----------------------------------------------------------------------+
        | %-15s | %-45s |
        | %-15s | %-45s |
        | %-15s | %-45s |
        +-----------------------------------------------------------------------+
        """,
                "Field", "Value",
                "Airport Code", airportCode,
                "Airport Name", airportName,
                "City", city
        );
    }


    /**


     * Retrieves the airportcode.


     */


    public String getAirportCode() {
        return airportCode;
    }

    /**

     * Updates the airportcode.

     */

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    /**

     * Retrieves the airportname.

     */

    public String getAirportName() {
        return airportName;
    }

    /**

     * Updates the airportname.

     */

    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }

    /**

     * Retrieves the city.

     */

    public String getCity() {
        return city;
    }

    /**

     * Updates the city.

     */

    public void setCity(String city) {
        this.city = city;
    }



}
