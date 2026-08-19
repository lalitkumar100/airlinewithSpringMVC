<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add New Flight</title>
    <!-- Bootstrap 5 CSS CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

    <div class="container py-5">
        <div class="row justify-content-center">
            <div class="col-md-8 col-lg-6">

                <!-- Card Container -->
                <div class="card shadow border-0">
                    <div class="card-header bg-primary text-white text-center py-3">
                        <h3 class="mb-0">Add New Flight</h3>
                    </div>
                    <div class="card-body p-4">

                        <!-- Dynamic Alert Message Box -->
                        <div id="message" class="alert d-none" role="alert"></div>

                        <form id="flightForm" class="needs-validation" novalidate>

                            <div class="mb-3">
                                <label for="sourceAirport" class="form-label fw-bold">Source Airport</label>
                                <select id="sourceAirport" class="form-select" required>
                                    <option value="">-- Select Source Airport --</option>
                                    <c:forEach var="airport" items="${airports}">
                                        <option value="${airport.airportCode}">${airport.city} (${airport.airportCode})</option>
                                    </c:forEach>
                                </select>
                                <div class="invalid-feedback">Please select a source airport.</div>
                            </div>

                            <div class="mb-3">
                                <label for="destinationAirport" class="form-label fw-bold">Destination Airport</label>
                                <select id="destinationAirport" class="form-select" required>
                                    <option value="">-- Select Destination Airport --</option>
                                    <c:forEach var="airport" items="${airports}">
                                        <option value="${airport.airportCode}">${airport.city} (${airport.airportCode})</option>
                                    </c:forEach>
                                </select>
                                <div class="invalid-feedback">Please select a destination airport.</div>
                            </div>

                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label for="departureDateTime" class="form-label fw-bold">Departure Date & Time</label>
                                    <input type="datetime-local" id="departureDateTime" class="form-control" required>
                                    <div class="invalid-feedback">Please select departure date and time.</div>
                                </div>
                                <div class="col-md-6 mb-3">
                                    <label for="arrivalDateTime" class="form-label fw-bold">Arrival Date & Time</label>
                                    <input type="datetime-local" id="arrivalDateTime" class="form-control" required>
                                    <div class="invalid-feedback">Please select arrival date and time.</div>
                                </div>
                            </div>

                            <div class="mb-3">
                                <label for="aircraftId" class="form-label fw-bold">Aircraft</label>
                                <select id="aircraftId" class="form-select" required>
                                    <option value="">-- Select Aircraft --</option>
                                    <c:forEach var="aircraft" items="${aircrafts}">
                                        <option value="${aircraft.aircraftId}">${aircraft.model} (Capacity: ${aircraft.capacity})</option>
                                    </c:forEach>
                                </select>
                                <div class="invalid-feedback">Please select an aircraft.</div>
                            </div>

                            <div class="mb-4">
                                <label for="baseFare" class="form-label fw-bold">Base Fare ($)</label>
                                <input type="number" step="0.01" id="baseFare" class="form-control" placeholder="0.00" required>
                                <div class="invalid-feedback">Please enter a valid base fare.</div>
                            </div>

                            <div class="d-grid">
                                <button type="submit" class="btn btn-primary btn-lg">Submit Flight</button>
                            </div>

                        </form>

                    </div>
                </div>

            </div>
        </div>
    </div>

    <!-- Bootstrap 5 JS Bundle CDN -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

    <script>
        document.getElementById('flightForm').addEventListener('submit', function(e) {
            e.preventDefault();

            const msgDiv = document.getElementById('message');
            msgDiv.classList.remove('d-none', 'alert-success', 'alert-danger');

            const flightData = {
                source: { airportCode: document.getElementById('sourceAirport').value },
                destination: { airportCode: document.getElementById('destinationAirport').value },
                departureDateTime: document.getElementById('departureDateTime').value,
                arrivalDateTime: document.getElementById('arrivalDateTime').value,
                aircraft: { aircraftId: document.getElementById('aircraftId').value },
                baseFare: parseFloat(document.getElementById('baseFare').value)
            };

            const contextPath = '${pageContext.request.contextPath}';

            fetch(contextPath + '/api/v1/flights', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(flightData)
            })
            .then(response => {
                if (response.ok) {
                    msgDiv.classList.add('alert-success');
                    msgDiv.innerText = 'Flight successfully created!';
                    document.getElementById('flightForm').reset();
                } else {
                    return response.json().then(err => {
                        throw new Error(err.message || 'Failed to create flight');
                    });
                }
            })
            .catch(error => {
                msgDiv.classList.add('alert-danger');
                msgDiv.innerText = 'Error: ' + error.message;
            });
        });
    </script>

</body>
</html>