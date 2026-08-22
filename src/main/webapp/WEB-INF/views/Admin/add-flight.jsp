<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Add New Flight</title>

    <!-- Bootstrap 5 -->
    <link
        href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
        rel="stylesheet">
</head>

<body class="bg-light">

<div class="container py-5">

    <div class="row justify-content-center">

        <div class="col-md-8 col-lg-6">

            <div class="card shadow border-0">

                <!-- Header -->
                <div class="card-header bg-primary text-white text-center py-3">
                    <h3 class="mb-0">Add New Flight</h3>
                </div>

                <!-- Body -->
                <div class="card-body p-4">

                    <!-- Message -->
                    <div
                        id="message"
                        class="alert d-none"
                        role="alert">
                    </div>

                    <form
                        id="flightForm"
                        class="needs-validation"
                        novalidate>

                        <!-- SOURCE AIRPORT -->
                        <div class="mb-3">

                            <label
                                for="sourceAirport"
                                class="form-label fw-bold">
                                Source Airport
                            </label>

                            <select
                                id="sourceAirport"
                                class="form-select"
                                required>

                                <option value="">
                                    -- Select Source Airport --
                                </option>

                                <c:forEach
                                    var="airport"
                                    items="${airports}">

                                    <option value="${airport.airportCode}">
                                        ${airport.city} (${airport.airportCode})
                                    </option>

                                </c:forEach>

                            </select>

                            <div class="invalid-feedback">
                                Please select a source airport.
                            </div>

                        </div>


                        <!-- DESTINATION AIRPORT -->
                        <div class="mb-3">

                            <label
                                for="destinationAirport"
                                class="form-label fw-bold">

                                Destination Airport

                            </label>

                            <select
                                id="destinationAirport"
                                class="form-select"
                                required>

                                <option value="">
                                    -- Select Destination Airport --
                                </option>

                                <c:forEach
                                    var="airport"
                                    items="${airports}">

                                    <option value="${airport.airportCode}">
                                        ${airport.city} (${airport.airportCode})
                                    </option>

                                </c:forEach>

                            </select>

                            <div class="invalid-feedback">
                                Please select a destination airport.
                            </div>

                        </div>


                        <!-- DATE TIME -->
                        <div class="row">

                            <!-- DEPARTURE -->
                            <div class="col-md-6 mb-3">

                                <label
                                    for="departureDateTime"
                                    class="form-label fw-bold">

                                    Departure Date & Time

                                </label>

                                <input
                                    type="datetime-local"
                                    id="departureDateTime"
                                    class="form-control"
                                    required>

                                <div class="invalid-feedback">
                                    Please select departure date and time.
                                </div>

                            </div>


                            <!-- ARRIVAL -->
                            <div class="col-md-6 mb-3">

                                <label
                                    for="arrivalDateTime"
                                    class="form-label fw-bold">

                                    Arrival Date & Time

                                </label>

                                <input
                                    type="datetime-local"
                                    id="arrivalDateTime"
                                    class="form-control"
                                    required>

                                <div class="invalid-feedback">
                                    Please select arrival date and time.
                                </div>

                            </div>

                        </div>


                        <!-- AIRCRAFT -->
                        <div class="mb-3">

                            <label
                                for="aircraftId"
                                class="form-label fw-bold">

                                Aircraft

                            </label>

                            <select
                                id="aircraftId"
                                class="form-select"
                                required>

                                <option value="">
                                    -- Select Aircraft --
                                </option>

                                <c:forEach
                                    var="aircraft"
                                    items="${aircrafts}">

                                    <option value="${aircraft.aircraftId}">
                                        ${aircraft.model}
                                        (Capacity: ${aircraft.capacity})
                                    </option>

                                </c:forEach>

                            </select>

                            <div class="invalid-feedback">
                                Please select an aircraft.
                            </div>

                        </div>


                        <!-- BASE FARE -->
                        <div class="mb-4">

                            <label
                                for="baseFare"
                                class="form-label fw-bold">

                                Base Fare ($)

                            </label>

                            <input
                                type="number"
                                step="0.01"
                                min="0"
                                id="baseFare"
                                class="form-control"
                                placeholder="0.00"
                                required>

                            <div class="invalid-feedback">
                                Please enter a valid base fare.
                            </div>

                        </div>


                        <!-- SUBMIT -->
                        <div class="d-grid">

                            <button
                                type="submit"
                                class="btn btn-primary btn-lg">

                                Submit Flight

                            </button>

                        </div>

                    </form>

                </div>

            </div>

        </div>

    </div>

</div>


<!-- Bootstrap JS -->
<script
    src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js">
</script>


<script>

document.getElementById('flightForm').addEventListener('submit', function(e) {

    e.preventDefault();

    const form = this;

    /*
     * Bootstrap validation
     */
    if (!form.checkValidity()) {

        e.stopPropagation();

        form.classList.add('was-validated');

        return;
    }

    form.classList.add('was-validated');


    const msgDiv = document.getElementById('message');

    msgDiv.classList.remove(
        'd-none',
        'alert-success',
        'alert-danger'
    );


    /*
     * datetime-local gives:
     *
     * 2026-08-21T09:00
     *
     * LocalDateTime can be sent as:
     *
     * 2026-08-21T09:00:00
     *
     * So we add :00 seconds.
     */
    function formatDateTime(value) {

        if (!value) {
            return null;
        }

        return value.length === 16
            ? value + ':00'
            : value;
    }


    /*
     * Get values
     */
    const sourceAirport =
        document.getElementById('sourceAirport').value;

    const destinationAirport =
        document.getElementById('destinationAirport').value;

    const departureDateTime =
        document.getElementById('departureDateTime').value;

    const arrivalDateTime =
        document.getElementById('arrivalDateTime').value;

    const aircraftId =
        document.getElementById('aircraftId').value;

    const baseFare =
        parseFloat(document.getElementById('baseFare').value);


    /*
     * Validation checks
     */
    const now = new Date();
    const depDate = new Date(departureDateTime);
    const arrDate = new Date(arrivalDateTime);

    // 1. Source and Destination cannot be the same
    if (sourceAirport === destinationAirport) {
        msgDiv.classList.add('alert-danger');
        msgDiv.innerText = 'Source and destination airports cannot be the same.';
        return;
    }

    // 2. Departure time must be in the future
    if (depDate <= now) {
        msgDiv.classList.add('alert-danger');
        msgDiv.innerText = 'Departure date and time must be in the future.';
        return;
    }

    // 3. Arrival time must be in the future
    if (arrDate <= now) {
        msgDiv.classList.add('alert-danger');
        msgDiv.innerText = 'Arrival date and time must be in the future.';
        return;
    }

    // 4. Arrival time must be after departure time
    if (depDate >= arrDate) {
        msgDiv.classList.add('alert-danger');
        msgDiv.innerText = 'Arrival time must be after departure time.';
        return;
    }


    /*
     * JSON data
     */
    const flightData = {

        source: {
            airportCode: sourceAirport
        },

        destination: {
            airportCode: destinationAirport
        },

        departureDateTime:
            formatDateTime(departureDateTime),

        arrivalDateTime:
            formatDateTime(arrivalDateTime),

        aircraft: {
            aircraftId: aircraftId
        },

        baseFare: baseFare
    };


    console.log(
        'Sending flight data:',
        flightData
    );


    /*
     * Context path
     */
    const contextPath =
        '${pageContext.request.contextPath}';


    /*
     * POST request
     */
    fetch(
        contextPath + '/api/v1/flights',
        {

            method: 'POST',

            headers: {
                'Content-Type': 'application/json'
            },

            body: JSON.stringify(flightData)

        }
    )

    .then(async response => {

        if (response.ok) {

            msgDiv.classList.add(
                'alert-success'
            );

            msgDiv.innerText =
                'Flight successfully created!';

            form.reset();

            form.classList.remove(
                'was-validated'
            );

        } else {

            let errorMessage =
                'Failed to create flight';

            try {

                const error =
                    await response.json();

                errorMessage =
                    error.message ||
                    error.error ||
                    errorMessage;

            } catch (e) {
                // Ignore JSON parsing error
            }

            throw new Error(errorMessage);
        }

    })

    .catch(error => {

        msgDiv.classList.add(
            'alert-danger'
        );

        msgDiv.innerText =
            'Error: ' + error.message;

        console.error(
            'Flight creation error:',
            error
        );

    });

});

</script>

</body>
</html>