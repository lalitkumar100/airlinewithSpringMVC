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

    @Id
    @Column(name = "airport_code", length = 3)
    private String airportCode;

    @Column(name = "airport_name", nullable = false, length = 100)
    private String airportName;

    @Column(nullable = false, length = 100)
    private String city;

    @OneToMany(mappedBy = "source")
    @JsonIgnore
    private List<Flight> departingFlights = new ArrayList<>();

    @OneToMany(mappedBy = "destination")
    @JsonIgnore
    private List<Flight> arrivingFlights = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_deleted")
    private boolean deleted;

    public Airport() {
    }

    public Airport(String airportCode, String airportName, String city) {
        this.airportCode = airportCode;
        this.airportName = airportName;
        this.city = city;
    }

    public static void printHeader() {
        System.out.printf(
                "%-15s %-35s %-25s%n",
                "Airport Code",
                "Airport Name",
                "City"
        );
        System.out.println("---------------------------------------------------------------------------");
    }

    public String toRow() {
        return String.format(
                "%-15s %-35s %-25s",
                airportCode,
                airportName,
                city
        );
    }

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

    public List<Flight> getDepartingFlights() {
        return departingFlights;
    }

    public void setDepartingFlights(List<Flight> departingFlights) {
        this.departingFlights = departingFlights;
    }

    public List<Flight> getArrivingFlights() {
        return arrivingFlights;
    }

    public void setArrivingFlights(List<Flight> arrivingFlights) {
        this.arrivingFlights = arrivingFlights;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
