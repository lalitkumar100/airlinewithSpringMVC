package com.crimsonlogic.arilinemanangmentsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing a Aircraft in the system.
 */
@Entity
@Table(name = "aircraft")
public class Aircraft {

    @Id
    @Column(name = "aircraft_id", length = 20)
    private String aircraftId;

    @Column(nullable = false, length = 100)
    private String model;

    @Column(nullable = false)
    private int capacity;

    @OneToMany(mappedBy = "aircraft")
    @JsonIgnore
    private List<Flight> flights = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_deleted")
    private boolean deleted;

    public Aircraft() {
    }

    public Aircraft(String aircraftId, String model, int capacity) {
        this.aircraftId = aircraftId;
        this.model = model;
        this.capacity = capacity;
    }

    public static void printHeader() {
        System.out.printf(
                "%-15s %-35s %-10s%n",
                "Aircraft ID",
                "Model",
                "Capacity"
        );
        System.out.println("---------------------------------------------------------------");
    }

    public String toRow() {
        return String.format(
                "%-15s %-35s %-10d",
                aircraftId,
                model,
                capacity
        );
    }

    @Override
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

    public void displayInfo() {
        System.out.println("\n========== AIRCRAFT DETAILS ==========");
        System.out.println("Aircraft ID : " + aircraftId);
        System.out.println("Model       : " + model);
        System.out.println("Capacity    : " + capacity);
    }

    public void setAircraftId(String aircraftId) {
        this.aircraftId = aircraftId;
    }

    public String getAircraftId() {
        return aircraftId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
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
