<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add New Flight</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; }
        .form-group { margin-bottom: 15px; }
        label { display: block; margin-bottom: 5px; font-weight: bold; }
        select, input { width: 100%; padding: 8px; box-sizing: border-box; }
        button { padding: 10px 15px; background-color: #007bff; color: white; border: none; cursor: pointer; }
        button:hover { background-color: #0056b3; }
        #message { margin-top: 15px; font-weight: bold; }
    </style>
</head>
<body>

    <h2>Add New Flight</h2>

    <div id="message"></div>

    <form id="flightForm">
        <div class="form-group">
            <label for="sourceAirport">Source Airport:</label>
            <select id="sourceAirport" required>
                <option value="">-- Select Source Airport --</option>
                <c:forEach var="airport" items="${airports}">
                    <option value="${airport.airportCode}">${airport.city} (${airport.airportCode})</option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label for="destinationAirport">Destination Airport:</label>
            <select id="destinationAirport" required>
                <option value="">-- Select Destination Airport --</option>
                <c:forEach var="airport" items="${airports}">
                    <option value="${airport.airportCode}">${airport.city} (${airport.airportCode})</option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label for="departureDateTime">Departure Date & Time:</label>
            <input type="datetime-local" id="departureDateTime" required>
        </div>

        <div class="form-group">
            <label for="arrivalDateTime">Arrival Date & Time:</label>
            <input type="datetime-local" id="arrivalDateTime" required>
        </div>

        <div class="form-group">
            <label for="aircraftId">Aircraft:</label>
            <select id="aircraftId" required>
                <option value="">-- Select Aircraft --</option>
                <c:forEach var="aircraft" items="${aircrafts}">
                    <option value="${aircraft.aircraftId}">${aircraft.model} (Capacity: ${aircraft.capacity})</option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label for="baseFare">Base Fare:</label>
            <input type="number" step="0.01" id="baseFare" required>
        </div>

        <button type="submit">Submit Flight</button>
    </form>

    <script>
        document.getElementById('flightForm').addEventListener('submit', function(e) {
            e.preventDefault();

            const flightData = {
                source: {
                    airportCode: document.getElementById('sourceAirport').value
                },
                destination: {
                    airportCode: document.getElementById('destinationAirport').value
                },
                departureDateTime: document.getElementById('departureDateTime').value,
                arrivalDateTime: document.getElementById('arrivalDateTime').value,
                aircraft: {
                    aircraftId: document.getElementById('aircraftId').value
                },
                baseFare: parseFloat(document.getElementById('baseFare').value)
            };

            const contextPath = '${pageContext.request.contextPath}';

            fetch(contextPath + '/api/v1/flights', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(flightData)
            })
            .then(response => {
                const msgDiv = document.getElementById('message');
                if (response.ok) {
                    msgDiv.style.color = 'green';
                    msgDiv.innerText = 'Flight successfully created!';
                    document.getElementById('flightForm').reset();
                } else {
                    return response.json().then(err => {
                        throw new Error(err.message || 'Failed to create flight');
                    });
                }
            })
            .catch(error => {
                const msgDiv = document.getElementById('message');
                msgDiv.style.color = 'red';
                msgDiv.innerText = 'Error: ' + error.message;
            });
        });
    </script>

</body>
</html>