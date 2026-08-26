package com.crimsonlogic.arilinemanangmentsystem.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Data Transfer Object for airport dto.
 * Used to transfer data between the client and the server.
 */
public class AirportDTO {

    @NotBlank(message = "Airport code is required")
    @Size(min = 3, max = 3, message = "Airport code must be exactly 3 characters")
    @Pattern(
            regexp = "^[A-Z]{3}$",
            message = "Airport code must contain exactly 3 uppercase letters"
    )
    /**
     * The airport code.
     */
    private String airportCode;

    /**
     * The airport name.
     */
    @NotBlank(message = "Airport name is required")
    @Size(max = 100, message = "Airport name must not exceed 100 characters")
    private String airportName;

    /**
     * The city.
     */
    @NotBlank(message = "City is required")
    @Size(max = 100, message = "City must not exceed 100 characters")
    private String city;

    public AirportDTO() {
    }

    public AirportDTO(String airportCode, String airportName, String city) {
        this.airportCode = airportCode;
        this.airportName = airportName;
        this.city = city;
    }

    /**
     * Retrieves the airport code.
     * @return String the result of the operation
     */
    public String getAirportCode() {
        return airportCode;
    }

    /**
     * Sets the airport code.
     * @param airportCode the airport code
     */
    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    /**
     * Retrieves the airport name.
     * @return String the result of the operation
     */
    public String getAirportName() {
        return airportName;
    }

    /**
     * Sets the airport name.
     * @param airportName the airport name
     */
    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }

    /**
     * Retrieves the city.
     * @return String the result of the operation
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city.
     * @param city the city
     */
    public void setCity(String city) {
        this.city = city;
    }
}