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

    /**
     * The aircraft id.
     */
    @Id
    @Column(name = "aircraft_id", length = 20)
    private String aircraftId;

    /**
     * The model.
     */
    @Column(nullable = false, length = 100)
    private String model;

    /**
     * The capacity.
     */
    @Column(nullable = false)
    private int capacity;

    @OneToMany(mappedBy = "aircraft")
    @JsonIgnore
    private List<Flight> flights = new ArrayList<>();

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

    public Aircraft() {
    }

    public Aircraft(String aircraftId, String model, int capacity) {
        this.aircraftId = aircraftId;
        this.model = model;
        this.capacity = capacity;
    }

    /**
     * Executes the print header operation.
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
     * Executes the to row operation.
     * @return String the result of the operation
     */
    public String toRow() {
        return String.format(
                "%-15s %-35s %-10d",
                aircraftId,
                model,
                capacity
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
     * Executes the display info operation.
     */
    public void displayInfo() {
        System.out.println("\n========== AIRCRAFT DETAILS ==========");
        System.out.println("Aircraft ID : " + aircraftId);
        System.out.println("Model       : " + model);
        System.out.println("Capacity    : " + capacity);
    }

    /**
     * Sets the aircraft id.
     * @param aircraftId the aircraft id
     */
    public void setAircraftId(String aircraftId) {
        this.aircraftId = aircraftId;
    }

    /**
     * Retrieves the aircraft id.
     * @return String the result of the operation
     */
    public String getAircraftId() {
        return aircraftId;
    }

    /**
     * Retrieves the model.
     * @return String the result of the operation
     */
    public String getModel() {
        return model;
    }

    /**
     * Sets the model.
     * @param model the model
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Retrieves the capacity.
     * @return int the result of the operation
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Sets the capacity.
     * @param capacity the capacity
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Retrieves the flights.
     * @return List<Flight> the result of the operation
     */
    public List<Flight> getFlights() {
        return flights;
    }

    /**
     * Sets the flights.
     * @param flights the flights
     */
    public void setFlights(List<Flight> flights) {
        this.flights = flights;
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
