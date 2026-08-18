<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Flight Search Results</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="container mt-5">
    <h2>Flight Search Results</h2>
    <p class="text-muted">
        Showing results for <strong id="lblSource"></strong> &rarr; <strong id="lblDestination"></strong> on <strong id="lblDate"></strong>
    </p>
    
    <a href="${pageContext.request.contextPath}/flights/search-form" class="btn btn-secondary mb-3">Modify Search</a>

    <!-- Alert for no flights -->
    <div id="noFlightsAlert" class="alert alert-warning d-none" role="alert">
        No flights found matching your criteria.
    </div>

    <!-- Flight Table -->
    <table id="flightTable" class="table table-striped table-bordered d-none">
        <thead class="table-dark">
            <tr>
                <th>Flight Code</th>
                <th>Source</th>
                <th>Destination</th>
                <th>Departure Time</th>
                <th>Arrival Time</th>
                <th>Base Fare</th>
                <th>Status</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody id="flightTableBody">
            <!-- Data will be populated dynamically via JavaScript -->
        </tbody>
    </table>

    <!-- JavaScript to fetch data from REST API -->
    <script>
        // Extract query parameters from the URL (e.g., ?source=AMD&destination=COK&departureDate=2026-10-01)
        const urlParams = new URLSearchParams(window.location.search);
        const source = urlParams.get('source');
        const destination = urlParams.get('destination');
        const departureDate = urlParams.get('departureDate');

        // Set labels on the page
        document.getElementById('lblSource').innerText = source || '';
        document.getElementById('lblDestination').innerText = destination || '';
        document.getElementById('lblDate').innerText = departureDate || '';

        if (source && destination && departureDate) {
            // Construct your REST API URL matching your format
            const apiUrl = `${pageContext.request.contextPath}/api/v1/flights/search?source=\${source}&destination=\${destination}&date=\${departureDate}`;

            fetch(apiUrl)
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    return response.json();
                })
                .then(flights => {
                    const tableBody = document.getElementById('flightTableBody');
                    const table = document.getElementById('flightTable');
                    const noFlightsAlert = document.getElementById('noFlightsAlert');

                    tableBody.innerHTML = '';

                    if (!flights || flights.length === 0) {
                        noFlightsAlert.classList.remove('d-none');
                        table.classList.add('d-none');
                    } else {
                        noFlightsAlert.classList.add('d-none');
                        table.classList.remove('d-none');

                        flights.forEach(flight => {
                            const row = document.createElement('tr');
                            row.innerHTML = `
                                <td>\${flight.flightCode}</td>
                                <td>\${flight.source.airportCode} (\${flight.source.city})</td>
                                <td>\${flight.destination.airportCode} (\${flight.destination.city})</td>
                                <td>\${flight.departureDateTime}</td>
                                <td>\${flight.arrivalDateTime}</td>
                                <td>$\${flight.baseFare}</td>
                                <td><span class="badge bg-info">\${flight.status}</span></td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/bookings/new?flightId=\${flight.flightId}" class="btn btn-success btn-sm">Book Flight</a>
                                </td>
                            `;
                            tableBody.appendChild(row);
                        });
                    }
                })
                .catch(error => {
                    console.error('Error fetching flight search results:', error);
                    alert('An error occurred while fetching flight data.');
                });
        }
    </script>
</body>
</html>