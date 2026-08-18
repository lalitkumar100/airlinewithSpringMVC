<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Available Flights</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">

    <style>

        :root {
            --navy: #0b1f3a;
        }

        body {
            background: #f4f7fb;
        }

        .flight-card {
            border: none;
            border-radius: 18px;
            overflow: hidden;
        }

        .flight-header {
            background: var(--navy);
            color: white;
        }

        .brand-icon {
            font-size: 30px;
        }

        .table thead th {
            background: var(--navy);
            color: white;
            white-space: nowrap;
        }

        .table tbody {
            vertical-align: middle;
        }

        .table tbody tr:hover {
            background-color: #f1f5f9;
        }

        .flight-code {
            font-weight: 700;
            color: var(--navy);
        }

        .airport-name {
            font-weight: 600;
            color: var(--navy);
        }

        .airport-code {
            font-size: 12px;
            color: #6c757d;
        }

        .city-name {
            font-size: 12px;
            color: #6c757d;
        }

    </style>

</head>

<body>

<div class="container-fluid px-3 px-md-4 py-4">

    <div class="card flight-card shadow-lg">

        <!-- Header -->
        <div class="flight-header p-4">

            <div class="d-flex justify-content-between align-items-center">

                <div>

                    <div class="brand-icon">
                        ✈
                    </div>

                    <h2 class="fw-bold mb-1">
                        Available Flights
                    </h2>

                    <p class="mb-0 opacity-75">
                        Find available flights for your journey
                    </p>

                </div>

                <span class="badge bg-light text-dark fs-6 px-3 py-2">
                    Passenger
                </span>

            </div>

        </div>


        <!-- Flight Table -->
        <div class="card-body p-0">

            <div class="table-responsive">

                <table class="table table-hover mb-0">

                    <thead>

                    <tr>

                        <th class="px-3 py-3">
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
                            Status
                        </th>

                    </tr>

                    </thead>


                    <tbody>

                    <c:forEach var="flight" items="${flights}">

                        <tr>

                            <!-- Flight ID -->
                            <td class="px-3">
                                    ${flight.flightId}
                            </td>


                            <!-- Flight Code -->
                            <td>
                                <span class="flight-code">
                                        ${flight.flightCode}
                                </span>
                            </td>


                            <!-- Source -->
                            <td>

                                <div class="airport-name">
                                        ${flight.source.airportName}
                                </div>

                                <div class="airport-code">
                                        ${flight.source.airportCode}
                                </div>

                                <div class="city-name">
                                        ${flight.source.city}
                                </div>

                            </td>


                            <!-- Destination -->
                            <td>

                                <div class="airport-name">
                                        ${flight.destination.airportName}
                                </div>

                                <div class="airport-code">
                                        ${flight.destination.airportCode}
                                </div>

                                <div class="city-name">
                                        ${flight.destination.city}
                                </div>

                            </td>


                            <!-- Departure -->
                            <td>
                                    ${flight.departureDateTime}
                            </td>


                            <!-- Arrival -->
                            <td>
                                    ${flight.arrivalDateTime}
                            </td>


                            <!-- Status -->
                            <td>
                                <span class="badge bg-primary">
                                        ${flight.status}
                                </span>
                            </td>

                        </tr>

                    </c:forEach>


                    <!-- No Flights -->
                    <c:if test="${empty flights}">

                        <tr>

                            <td colspan="7"
                                class="text-center text-muted py-5">

                                <div class="fs-1">
                                    ✈
                                </div>

                                <h5>
                                    No flights available
                                </h5>

                                <p class="mb-0">
                                    There are currently no available flights.
                                </p>

                            </td>

                        </tr>

                    </c:if>

                    </tbody>

                </table>

            </div>

        </div>

    </div>


    <!-- Footer -->
    <div class="text-center mt-3">

        <small class="text-muted">
            Airline Management System
        </small>

    </div>

</div>

</body>
</html>