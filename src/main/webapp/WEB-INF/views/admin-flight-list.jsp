<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1">

    <title>Admin - Flight Management</title>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">

    <!-- Font Awesome -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"
          rel="stylesheet">


    <style>

        :root {
            --navy: #003366;
            --navy-dark: #002244;
            --light-blue: #eaf3ff;
            --light-gray: #f5f8fc;
        }

        body {
            background-color: var(--light-gray);
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        .page-header {
            background: linear-gradient(
                    135deg,
                    var(--navy),
                    #0056a6
            );

            color: white;

            padding: 40px 0 55px;

            margin-bottom: -25px;
        }

        .page-header h2 {
            font-weight: 700;
        }

        .flight-card {
            border: none;
            border-radius: 15px;
            overflow: hidden;
            background: white;
            box-shadow: 0 8px 30px rgba(0, 51, 102, 0.12);
        }

        .table thead th {
            background-color: var(--navy);
            color: white;
            border: none;
            padding: 15px;
            font-size: 0.82rem;
            text-transform: uppercase;
            letter-spacing: 0.4px;
        }

        .table tbody td {
            padding: 15px;
            vertical-align: middle;
        }

        .table tbody tr {
            border-bottom: 1px solid #edf2f7;
        }

        .table tbody tr:hover {
            background-color: #f8fbff;
        }

        .flight-code {
            background-color: var(--light-blue);
            color: var(--navy);
            padding: 5px 9px;
            border-radius: 5px;
            font-weight: 700;
        }

        .airport-code {
            color: var(--navy);
            font-weight: 700;
        }

        .airport-city {
            color: #6c757d;
            font-size: 0.85rem;
        }

        .fare {
            font-weight: 700;
            color: #198754;
        }

        .status-badge {
            padding: 6px 12px;
            border-radius: 20px;
            font-weight: 600;
        }

        .manage-btn {
            min-width: 100px;
        }

        .loading-container {
            padding: 70px 20px;
        }

        .no-flight-container {
            padding: 70px 20px;
        }

    </style>

</head>


<body>


<!-- ===================================================== -->
<!-- HEADER -->
<!-- ===================================================== -->

<div class="page-header">

    <div class="container">

        <div class="d-flex justify-content-between align-items-center">

            <div>

                <h2 class="mb-1">

                    <i class="fas fa-plane me-2"></i>

                    Flight Management

                </h2>

                <p class="mb-0 opacity-75">

                    View and manage all flights

                </p>

            </div>

        </div>

    </div>

</div>


<!-- ===================================================== -->
<!-- MAIN CONTENT -->
<!-- ===================================================== -->

<div class="container-fluid px-3 px-md-5 py-4">


    <!-- Error Message -->

    <div id="errorMessage"
         class="alert alert-danger d-none">

        <i class="fas fa-circle-exclamation me-2"></i>

        <span id="errorText"></span>

    </div>


    <!-- Flight Card -->

    <div class="card flight-card">


        <!-- Loading -->

        <div id="loading"
             class="loading-container text-center">

            <div class="spinner-border text-primary"
                 style="width: 3rem; height: 3rem;"
                 role="status">

                <span class="visually-hidden">
                    Loading...
                </span>

            </div>

            <p class="text-muted mt-3 mb-0">

                Fetching flight records...

            </p>

        </div>


        <!-- ================================================= -->
        <!-- FLIGHT TABLE -->
        <!-- ================================================= -->

        <div id="flightTableContainer"
             class="d-none">

            <div class="table-responsive">

                <table class="table table-hover mb-0">

                    <thead>

                    <tr>

                        <th class="ps-4">
                            Flight ID
                        </th>

                        <th>
                            Flight Code
                        </th>

                        <th>
                            Source
                        </th>

                        <th>
                            Destination
                        </th>

                        <th>
                            Departure
                        </th>

                        <th>
                            Arrival
                        </th>

                        <th>
                            Aircraft
                        </th>

                        <th>
                            Capacity
                        </th>

                        <th>
                            Base Fare
                        </th>

                        <th>
                            Status
                        </th>

                        <th class="text-center pe-4">
                            Action
                        </th>

                    </tr>

                    </thead>


                    <tbody id="flightTableBody">

                    </tbody>

                </table>

            </div>

        </div>


        <!-- ================================================= -->
        <!-- NO FLIGHTS -->
        <!-- ================================================= -->

        <div id="noFlights"
             class="no-flight-container text-center d-none">

            <i class="fas fa-plane-slash display-3 text-secondary opacity-25"></i>

            <h5 class="mt-4 fw-bold">
                No Flights Found
            </h5>

            <p class="text-muted mb-0">
                There are currently no flights available.
            </p>

        </div>


    </div>


    <!-- ================================================= -->
    <!-- FOOTER -->
    <!-- ================================================= -->

    <div class="text-center mt-5 mb-4">

        <p class="text-muted small">

            &copy; 2026 Airline Management System |

            <i class="fas fa-shield-alt"></i>

            Administrator Portal

        </p>

    </div>


</div>


<script>


    // =====================================================
    // PAGE LOAD
    // =====================================================

    document.addEventListener(
        "DOMContentLoaded",
        function () {

            loadFlights();

        }
    );


    // =====================================================
    // LOAD ALL FLIGHTS
    //
    // GET /api/v1/admin/flights
    //
    // Authorization:
    // Bearer <JWT TOKEN>
    // =====================================================

    function loadFlights() {

        const contextPath =
            '${pageContext.request.contextPath}';


        // Get JWT token from browser storage

        const token =
            localStorage.getItem('jwtToken');


        // =================================================
        // TOKEN CHECK
        // =================================================

        if (!token) {

            window.location.href =
                contextPath + '/users/login';

            return;

        }


        // =================================================
        // API URL
        // =================================================

        const apiUrl =
            contextPath +
            '/api/v1/admin/flights';


        // =================================================
        // API REQUEST
        // =================================================

        fetch(apiUrl, {

            method: 'GET',

            headers: {

                'Accept':
                    'application/json',

                // Send JWT token

                'Authorization':
                    'Bearer ' + token

            }

        })


        // =================================================
        // HANDLE RESPONSE
        // =================================================

        .then(function (response) {


            // ---------------------------------------------
            // Unauthorized
            // ---------------------------------------------

            if (
                response.status === 401 ||
                response.status === 403
            ) {

                localStorage.removeItem(
                    'jwtToken'
                );


                window.location.href =
                    contextPath + '/users/login';


                throw new Error(
                    'Session expired. Please login again.'
                );

            }


            // ---------------------------------------------
            // Other HTTP errors
            // ---------------------------------------------

            if (!response.ok) {

                throw new Error(
                    'Unable to load flights. HTTP Status: ' +
                    response.status
                );

            }


            return response.json();

        })


        // =================================================
        // HANDLE JSON
        // =================================================

        .then(function (flights) {


            // Hide loading

            document
                .getElementById('loading')
                .classList.add('d-none');


            // ---------------------------------------------
            // No flights
            // ---------------------------------------------

            if (
                !flights ||
                flights.length === 0
            ) {

                document
                    .getElementById('noFlights')
                    .classList.remove('d-none');

                return;

            }


            // ---------------------------------------------
            // Display flights
            // ---------------------------------------------

            displayFlights(
                flights
            );

        })


        // =================================================
        // HANDLE ERROR
        // =================================================

        .catch(function (error) {


            document
                .getElementById('loading')
                .classList.add('d-none');


            showError(
                error.message ||
                'Unable to load flights.'
            );

        });

    }


    // =====================================================
    // DISPLAY FLIGHTS
    // =====================================================

    function displayFlights(flights) {


        const tableBody =
            document.getElementById(
                'flightTableBody'
            );


        tableBody.innerHTML = '';


        flights.forEach(function (flight) {


            const row =
                document.createElement('tr');


            row.innerHTML = `

                <!-- Flight ID -->

                <td class="ps-4">

                    <span class="font-monospace fw-semibold">

                        \${escapeHtml(
                            flight.flightId
                        )}

                    </span>

                </td>


                <!-- Flight Code -->

                <td>

                    <span class="flight-code">

                        \${escapeHtml(
                            flight.flightCode
                        )}

                    </span>

                </td>


                <!-- Source -->

                <td>

                    <div class="airport-code">

                        \${escapeHtml(
                            flight.source.airportCode
                        )}

                    </div>

                    <div class="airport-city">

                        \${escapeHtml(
                            flight.source.city
                        )}

                    </div>

                </td>


                <!-- Destination -->

                <td>

                    <div class="airport-code">

                        \${escapeHtml(
                            flight.destination.airportCode
                        )}

                    </div>

                    <div class="airport-city">

                        \${escapeHtml(
                            flight.destination.city
                        )}

                    </div>

                </td>


                <!-- Departure -->

                <td class="small">

                    \${formatDateTime(
                        flight.departureDateTime
                    )}

                </td>


                <!-- Arrival -->

                <td class="small">

                    \${formatDateTime(
                        flight.arrivalDateTime
                    )}

                </td>


                <!-- Aircraft -->

                <td>

                    <div class="fw-semibold">

                        \${escapeHtml(
                            flight.aircraft.model
                        )}

                    </div>

                    <small class="text-muted">

                        \${escapeHtml(
                            flight.aircraft.aircraftId
                        )}

                    </small>

                </td>


                <!-- Capacity -->

                <td>

                    <span class="badge bg-light text-dark border">

                        \${flight.aircraft.capacity}

                    </span>

                </td>


                <!-- Base Fare -->

                <td>

                    <span class="fare">

                        ₹\${Number(
                            flight.baseFare
                        ).toLocaleString('en-IN')}

                    </span>

                </td>


                <!-- Status -->

                <td>

                    <span class="status-badge \${getStatusClass(
                        flight.status
                    )}">

                        \${escapeHtml(
                            flight.status
                        )}

                    </span>

                </td>


                <!-- Manage Button -->

                <td class="text-center pe-4">

                    <button
                        type="button"
                        class="btn btn-primary manage-btn"
                        onclick="manageFlight('\${escapeHtml(flight.flightId)}')">

                        <i class="fas fa-gear me-1"></i>

                        Manage

                    </button>

                </td>

            `;


            tableBody.appendChild(
                row
            );

        });


        // Show table

        document
            .getElementById(
                'flightTableContainer'
            )
            .classList.remove('d-none');

    }


    // =====================================================
    // MANAGE FLIGHT
    //
    // Example:
    //
    // /admin/flights/FLT400161
    // =====================================================

    function manageFlight(flightId) {


        const contextPath =
            '${pageContext.request.contextPath}';


        window.location.href =
            contextPath +
            '/admin/flights/' +
            encodeURIComponent(
                flightId
            );

    }


    // =====================================================
    // STATUS CLASS
    // =====================================================

    function getStatusClass(status) {


        if (status === 'SCHEDULED') {

            return 'bg-success';

        }


        if (status === 'DELAYED') {

            return 'bg-warning text-dark';

        }


        if (status === 'CANCELLED') {

            return 'bg-danger';

        }


        if (status === 'COMPLETED') {

            return 'bg-primary';

        }


        return 'bg-secondary';

    }


    // =====================================================
    // FORMAT DATE AND TIME
    // =====================================================

    function formatDateTime(value) {


        if (!value) {

            return '-';

        }


        const date =
            new Date(value);


        if (
            isNaN(
                date.getTime()
            )
        ) {

            return value;

        }


        return date.toLocaleDateString(
            'en-IN'
        )
        +
        '<br>'
        +
        '<strong>'
        +
        date.toLocaleTimeString(
            'en-IN',
            {
                hour: '2-digit',
                minute: '2-digit'
            }
        )
        +
        '</strong>';

    }


    // =====================================================
    // ESCAPE HTML
    // =====================================================

    function escapeHtml(value) {


        if (
            value === null ||
            value === undefined
        ) {

            return '';

        }


        return String(value)

            .replace(
                /&/g,
                '&amp;'
            )

            .replace(
                /</g,
                '&lt;'
            )

            .replace(
                />/g,
                '&gt;'
            )

            .replace(
                /"/g,
                '&quot;'
            )

            .replace(
                /'/g,
                '&#039;'
            );

    }


    // =====================================================
    // SHOW ERROR
    // =====================================================

    function showError(message) {


        const errorBox =
            document.getElementById(
                'errorMessage'
            );


        document
            .getElementById(
                'errorText'
            )
            .innerText =
            message;


        errorBox
            .classList
            .remove(
                'd-none'
            );

    }


</script>


</body>

</html>