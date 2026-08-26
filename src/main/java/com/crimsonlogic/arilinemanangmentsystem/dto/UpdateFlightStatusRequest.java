package com.crimsonlogic.arilinemanangmentsystem.dto;

import com.crimsonlogic.arilinemanangmentsystem.enumrator.FlightStatus;
import javax.validation.constraints.NotNull;

/**
 * Data Transfer Object for update flight status request.
 * Used to transfer data between the client and the server.
 */
public class UpdateFlightStatusRequest {
    @NotNull(message = "Flight status cannot be null")
    private FlightStatus status;

    /**
     * Retrieves the status.
     * @return FlightStatus the result of the operation
     */
    public FlightStatus getStatus() {
        return status;
    }

    /**
     * Sets the status.
     * @param status the status
     */
    public void setStatus(FlightStatus status) {
        this.status = status;
    }
}
