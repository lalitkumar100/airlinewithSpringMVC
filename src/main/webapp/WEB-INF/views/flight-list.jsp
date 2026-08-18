<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Available Flights</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

    <style>

        :root {
            --navy: #003366;
            --navy-dark: #002244;
            --accent-gold: #FFD700;
            --light-gray: #f8f9fa;
        }

        body {
            background-color: var(--light-gray);
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        .page-header {
            background-color: var(--navy);
            color: white;
            padding: 40px 0 60px 0;
            margin-bottom: -30px;
        }

        .flight-card {
            border: none;
            border-radius: 12px;
            box-shadow: 0 8px 30px rgba(0,0,0,0.1);
            overflow: hidden;
            background: white;
        }

        .table thead th {
            background: var(--navy);
            color: white;
            font-weight: 600;
            text-transform: uppercase;
            font-size: 0.85rem;
            letter-spacing: 0.5px;
            border: none;
            padding: 15px;
        }

        .table tbody tr {
            transition: all 0.2s;
            border-bottom: 1px solid #edf2f7;
        }

        .table tbody tr:hover {
            background-color: #f8faff;
            transform: scale(1.002);
        }

        .flight-code {
            font-weight: 700;
            color: var(--navy);
            background: #e7efff;
            padding: 4px 8px;
            border-radius: 4px;
        }

        .airport-name {
            font-weight: 600;
            color: #334e68;
            font-size: 0.95rem;
        }

        .airport-code {
            font-weight: 700;
            color: var(--navy);
        }

        .fare {
            font-weight: 700;
            color: #2d3748;
            font-size: 1.1rem;
        }

        .badge-status {
            font-weight: 500;
            padding: 6px 12px;
            border-radius: 20px;
        }

        .extra-small {
            font-size: 0.75rem;
        }

        .text-navy {
            color: var(--navy);
        }

    </style>

</head>

<body>

<div class="page-header text-center">
    <div class="container">
        <h2 class="fw-bold"><i class="fas fa-list-ul me-2"></i>Available Flights</h2>
        <p class="lead opacity-75">Manage and view all scheduled flights</p>
    </div>
</div>

<div class="container-fluid px-3 px-md-5 py-4">

    <div class="card flight-card shadow-lg">

        <!-- Error Message -->
        <div id="errorMessage"
             class="alert alert-danger m-4 d-none">
        </div>


        <!-- Loading -->
        <div id="loading"
             class="text-center py-5">

            <div class="spinner-border text-primary"
                 role="status">
            </div>

            <p class="mt-3 text-muted">
                Fetching flight records...
            </p>

        </div>


        <!-- Flight Table -->
        <div class="card-body p-0 d-none"
             id="flightTableContainer">

            <div class="table-responsive">

                <table class="table table-hover align-middle mb-0">

                    <thead>

                    <tr>

                        <th class="ps-4">ID</th>
                        <th>Code</th>
                        <th>Source</th>
                        <th>Destination</th>
                        <th>Departure</th>
                        <th>Arrival</th>
                        <th>Aircraft</th>
                        <th>Capacity</th>
                        <th>Base Fare</th>
                        <th>Status</th>
                        <th class="pe-4 text-center">Bookings</th>

                    </tr>

                    </thead>

                    <tbody id="flightTableBody">

                    </tbody>

                </table>

            </div>

        </div>


        <!-- No Flights -->
        <div id="noFlights"
             class="text-center text-muted py-5 d-none">

            <div class="display-1 text-navy opacity-25 mb-4">
                <i class="fas fa-plane-slash"></i>
            </div>

            <h5 class="fw-bold text-dark">
                No flights available
            </h5>

            <p class="mb-0">
                There are currently no flight records in the system.
            </p>

        </div>

    </div>


    <!-- Footer -->
    <div class="text-center mt-5 mb-4">

        <p class="text-muted small">
            &copy; 2026 Airline Management System | <i class="fas fa-shield-alt"></i> Administrator Portal
        </p>

    </div>

</div>


<script>

    document.addEventListener("DOMContentLoaded", function () {

        loadFlights();

    });


    function loadFlights() {

        const contextPath = '${pageContext.request.contextPath}';

        fetch(contextPath + '/api/v1/flights', {
            method: 'GET',
            headers: {
                'Accept': 'application/json'
            }
        })

            .then(response => {

                if (!response.ok) {
                    throw new Error(
                        'Unable to load flights. HTTP Status: ' +
                        response.status
                    );
                }

                return response.json();

            })

            .then(flights => {

                document.getElementById('loading')
                    .classList.add('d-none');

                if (!flights || flights.length === 0) {

                    document.getElementById('noFlights')
                        .classList.remove('d-none');

                    return;
                }

                displayFlights(flights);

            })

            .catch(error => {

                document.getElementById('loading')
                    .classList.add('d-none');

                const errorMessage =
                    document.getElementById('errorMessage');

                errorMessage.textContent =
                    'Error loading flights: ' + error.message;

                errorMessage.classList.remove('d-none');

            });

    }


    function displayFlights(flights) {

        const tableBody =
            document.getElementById('flightTableBody');

        tableBody.innerHTML = '';


        flights.forEach(function (flight) {

            const row = document.createElement('tr');
            row.className = 'flight-row';

            row.innerHTML = `

                <!-- Flight ID -->
                <td class="ps-4 text-muted small">
                    \${escapeHtml(flight.flightId)}
                </td>


                <!-- Flight Code -->
                <td>
                    <span class="flight-code">
                        \${escapeHtml(flight.flightCode)}
                    </span>
                </td>


                <!-- Source -->
                <td>
                    <div class="airport-code">\${escapeHtml(flight.source.airportCode)}</div>
                    <div class="airport-name text-muted small">\${escapeHtml(flight.source.city)}</div>
                </td>


                <!-- Destination -->
                <td>
                    <div class="airport-code">\${escapeHtml(flight.destination.airportCode)}</div>
                    <div class="airport-name text-muted small">\${escapeHtml(flight.destination.city)}</div>
                </td>


                <!-- Departure -->
                <td class="small">
                    \${formatDateTime(flight.departureDateTime)}
                </td>


                <!-- Arrival -->
                <td class="small">
                    \${formatDateTime(flight.arrivalDateTime)}
                </td>


                <!-- Aircraft -->
                <td>
                    <div class="fw-bold">\${escapeHtml(flight.aircraft.model)}</div>
                    <div class="text-muted extra-small">\${escapeHtml(flight.aircraft.aircraftId)}</div>
                </td>


                <!-- Capacity -->
                <td>
                    <span class="badge bg-light text-dark">\${flight.aircraft.capacity}</span>
                </td>


                <!-- Base Fare -->
                <td>
                    <span class="fare text-navy">
                        ₹\${Number(flight.baseFare).toLocaleString('en-IN')}
                    </span>
                </td>


                <!-- Status -->
                <td>
                    <span class="badge badge-status \${flight.status === 'SCHEDULED' ? 'bg-success' : 'bg-warning text-dark'}">
                        \${escapeHtml(flight.status)}
                    </span>
                </td>


                <!-- Bookings -->

       <td class="pe-4 text-center">
    <button type="button"
            class="btn btn-primary"
            onclick="window.location.href='${pageContext.request.contextPath}/bookings/new?flightId=${flight.flightId}'">
        Booking
    </button>
</td>

            `;


            tableBody.appendChild(row);

        });


        document.getElementById('flightTableContainer')
            .classList.remove('d-none');

    }


    function formatDateTime(dateTime) {

        if (!dateTime) {
            return '-';
        }

        const parts = dateTime.split('T');

        if (parts.length !== 2) {
            return dateTime;
        }

        return parts[0] + '<br>' +
            '<strong>' + parts[1] + '</strong>';

    }


    function escapeHtml(value) {

        if (value === null || value === undefined) {
            return '';
        }

        return String(value)
            .replace(/&/g, '&amp;')
            .replace(/</g, '&lt;')
            .replace(/>/g, '&gt;')
            .replace(/"/g, '&quot;')
            .replace(/'/g, '&#039;');

    }

</script>


</body>
</html>