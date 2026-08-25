package com.crimsonlogic.arilinemanangmentsystem.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class AirportDTO {

    @NotBlank(message = "Airport code is required")
    @Size(min = 3, max = 3, message = "Airport code must be exactly 3 characters")
    @Pattern(
            regexp = "^[A-Z]{3}$",
            message = "Airport code must contain exactly 3 uppercase letters"
    )
    private String airportCode;

    @NotBlank(message = "Airport name is required")
    @Size(max = 100, message = "Airport name must not exceed 100 characters")
    private String airportName;

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

    public String getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    public String getAirportName() {
        return airportName;
    }

    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}