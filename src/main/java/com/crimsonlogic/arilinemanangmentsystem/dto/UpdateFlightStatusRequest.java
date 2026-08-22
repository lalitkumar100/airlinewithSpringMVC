package com.crimsonlogic.arilinemanangmentsystem.dto;

import com.crimsonlogic.arilinemanangmentsystem.enumrator.FlightStatus;
import javax.validation.constraints.NotNull;

public class UpdateFlightStatusRequest {
    @NotNull(message = "Flight status cannot be null")
    private FlightStatus status;

    public FlightStatus getStatus() {
        return status;
    }

    public void setStatus(FlightStatus status) {
        this.status = status;
    }
}
