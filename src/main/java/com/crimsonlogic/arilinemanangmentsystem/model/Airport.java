package com.crimsonlogic.arilinemanangmentsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing an Airport in the system.
 */
@Entity
@Table(name = "airport")
public class Airport {

    /**
     * The airport code.
     */
    @Id
    @Column(name = "airport_code", length = 3)
    private String airportCode;

    /**
     * The airport name.
     */
    @Column(name = "airport_name", nullable = false, length = 100)
    private String airportName;

    /**
     * The city.
     */
    @Column(nullable = false, length = 100)
    private String city;

    @OneToMany(mappedBy = "source")
    @JsonIgnore
    private List<Flight> departingFlights = new ArrayList<>();

    @OneToMany(mappedBy = "destination")
    @JsonIgnore
    private List<Flight> arrivingFlights = new ArrayList<>();

    /**
     * The created at.
     */
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    /**
     * The updated at.
     */
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * The deleted.
     */
    @Column(name = "is_deleted")
    private boolean deleted;

    public Airport() {
    }

    public Airport(String airportCode, String airportName, String city) {
        this.airportCode = airportCode;
        this.airportName = airportName;
        this.city = city;
    }

    /**
     * Executes the print header operation.
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
     * Executes the to row operation.
     * @return String the result of the operation
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
     * Executes the to string operation.
     * @return String the result of the operation
     */
    @Override
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

    /**
     * Retrieves the departing flights.
     * @return List<Flight> the result of the operation
     */
    public List<Flight> getDepartingFlights() {
        return departingFlights;
    }

    /**
     * Sets the departing flights.
     * @param departingFlights the departing flights
     */
    public void setDepartingFlights(List<Flight> departingFlights) {
        this.departingFlights = departingFlights;
    }

    /**
     * Retrieves the arriving flights.
     * @return List<Flight> the result of the operation
     */
    public List<Flight> getArrivingFlights() {
        return arrivingFlights;
    }

    /**
     * Sets the arriving flights.
     * @param arrivingFlights the arriving flights
     */
    public void setArrivingFlights(List<Flight> arrivingFlights) {
        this.arrivingFlights = arrivingFlights;
    }

    /**
     * Retrieves the created at.
     * @return LocalDateTime the result of the operation
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the created at.
     * @param createdAt the created at
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Retrieves the updated at.
     * @return LocalDateTime the result of the operation
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Sets the updated at.
     * @param updatedAt the updated at
     */
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * Checks if the object is deleted.
     * @return boolean the result of the operation
     */
    public boolean isDeleted() {
        return deleted;
    }

    /**
     * Sets the deleted.
     * @param deleted the deleted
     */
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
