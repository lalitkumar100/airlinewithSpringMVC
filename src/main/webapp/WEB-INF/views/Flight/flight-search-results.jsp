<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>

    <title>Flight Search Results | Air Earth</title>

    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">

    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

    <style>

        :root {
            --navy-blue: #003366;
            --navy-dark: #002244;
            --light-gray: #f8f9fa;
        }

        body {
            background-color: var(--light-gray);
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        .page-header {
            background-color: var(--navy-blue);
            color: white;
            padding: 60px 0 80px 0;
        }

        .search-summary-card {
            background: white;
            border-left: 5px solid var(--navy-blue);
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0,0,0,0.05);
            margin-top: -30px;
            padding: 20px;
        }

        .btn-navy {
            background-color: var(--navy-blue);
            color: white;
            border: none;
        }

        .btn-navy:hover {
            background-color: var(--navy-dark);
            color: white;
        }

        .btn-outline-navy {
            border: 2px solid var(--navy-blue);
            color: var(--navy-blue);
            font-weight: 600;
        }

        .btn-outline-navy:hover {
            background-color: var(--navy-blue);
            color: white;
        }

        .text-navy {
            color: var(--navy-blue);
        }

        .table thead {
            background-color: var(--navy-blue);
            color: white;
        }

        .flight-row:hover {
            background-color: #f0f4f8;
            transition: background-color 0.3s;
        }

        .price-tag {
            font-weight: bold;
            color: var(--navy-blue);
            font-size: 1.2rem;
        }

        .badge-status {
            font-size: 0.85rem;
            padding: 0.5em 1em;
        }

        .flight-code {
            font-size: 1.05rem;
            font-weight: 700;
        }

    </style>

</head>

<body>

<!-- ================= HEADER ================= -->

<div class="page-header text-center">

    <div class="container">

        <h1 class="display-5 fw-bold">

            <i class="fas fa-plane-departure me-2"></i>

            Flight Search Results

        </h1>

        <p class="lead opacity-75">
            Fly high with luxury and comfort
        </p>

    </div>

</div>


<div class="container">

    <!-- ================= SEARCH SUMMARY ================= -->

    <div class="search-summary-card mb-5">

        <div class="row align-items-center">

            <div class="col-md-8">

                <h5 class="mb-1 text-muted">
                    Journey Details
                </h5>

                <div class="d-flex align-items-center flex-wrap">

                    <span id="lblSource"
                          class="fs-4 fw-bold text-navy">
                    </span>

                    <i class="fas fa-long-arrow-alt-right mx-3 fs-4 text-secondary"></i>

                    <span id="lblDestination"
                          class="fs-4 fw-bold text-navy">
                    </span>

                    <span class="ms-md-4 text-muted fs-6">

                        <i class="far fa-calendar-alt me-2"></i>

                        <span id="lblDate"></span>

                    </span>

                </div>

            </div>


            <div class="col-md-4 text-md-end mt-3 mt-md-0">

                <a href="${pageContext.request.contextPath}/flights/search-form"
                   class="btn btn-outline-navy rounded-pill px-4">

                    <i class="fas fa-edit me-2"></i>

                    Modify Search

                </a>

            </div>

        </div>

    </div>


    <!-- ================= NO FLIGHTS ================= -->

    <div id="noFlightsAlert"
         class="alert alert-info d-none shadow-sm border-0">

        <div class="d-flex align-items-center">

            <i class="fas fa-info-circle fs-4 me-3"></i>

            <div>

                <strong>No flights found.</strong>

                We couldn't find any flights matching your criteria.

                Please try different dates or locations.

            </div>

        </div>

    </div>


    <!-- ================= ERROR ================= -->

    <div id="errorAlert"
         class="alert alert-danger d-none shadow-sm border-0">

        <div class="d-flex align-items-center">

            <i class="fas fa-exclamation-circle fs-4 me-3"></i>

            <div id="errorMessage">

                Unable to retrieve flights.

            </div>

        </div>

    </div>


    <!-- ================= FLIGHT TABLE ================= -->

    <div id="flightTableContainer"
         class="card shadow-sm border-0 d-none">

        <div class="card-body p-0">

            <div class="table-responsive">

                <table class="table table-hover align-middle mb-0">

                    <thead>

                    <tr>

                        <th class="ps-4">
                            Flight
                        </th>

                        <th>
                            Route
                        </th>

                        <th>
                            Schedule
                        </th>

                        <th>
                            Aircraft
                        </th>

                        <th>
                            Fare
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

    </div>

</div>


<script>

    // =========================================================
    // GET SEARCH PARAMETERS
    // =========================================================

    const urlParams = new URLSearchParams(window.location.search);

    const source = urlParams.get("source");

    const destination = urlParams.get("destination");

    const departureDate = urlParams.get("departureDate");


    // =========================================================
    // DISPLAY SEARCH DETAILS
    // =========================================================

    document.getElementById("lblSource").innerText =
        source || "N/A";

    document.getElementById("lblDestination").innerText =
        destination || "N/A";

    document.getElementById("lblDate").innerText =
        departureDate || "N/A";


    // =========================================================
    // CALL API
    // =========================================================

    if (source && destination && departureDate) {

        const apiUrl =
            "${pageContext.request.contextPath}/api/v1/flights/search"
            + "?source=" + encodeURIComponent(source)
            + "&destination=" + encodeURIComponent(destination)
            + "&date=" + encodeURIComponent(departureDate);

        console.log("Calling API:", apiUrl);


        fetch(apiUrl)

            .then(response => {

                if (!response.ok) {

                    throw new Error(
                        "HTTP Error: " + response.status
                    );

                }

                return response.json();

            })


            .then(data => {

                console.log("Complete API Response:", data);


                const tableBody =
                    document.getElementById("flightTableBody");

                const tableContainer =
                    document.getElementById("flightTableContainer");

                const noFlightsAlert =
                    document.getElementById("noFlightsAlert");

                const errorAlert =
                    document.getElementById("errorAlert");


                tableBody.innerHTML = "";


                // =================================================
                // CHECK API STATUS
                // =================================================

                if (data.status !== "SUCCESS") {

                    tableContainer.classList.add("d-none");

                    noFlightsAlert.classList.add("d-none");

                    errorAlert.classList.remove("d-none");

                    document.getElementById("errorMessage").innerText =
                        data.message || "Unable to retrieve flights.";

                    return;
                }


                // =================================================
                // GET responseData
                // =================================================

                const flights = Array.isArray(data.responseData)
                    ? data.responseData
                    : [];


                console.log("Flights:", flights);


                // =================================================
                // NO FLIGHTS
                // =================================================

                if (flights.length === 0) {

                    tableContainer.classList.add("d-none");

                    errorAlert.classList.add("d-none");

                    noFlightsAlert.classList.remove("d-none");

                    return;
                }


                // =================================================
                // FLIGHTS FOUND
                // =================================================

                noFlightsAlert.classList.add("d-none");

                errorAlert.classList.add("d-none");

                tableContainer.classList.remove("d-none");


                // =================================================
                // DISPLAY FLIGHTS
                // =================================================

                flights.forEach(flight => {

                    const row = document.createElement("tr");

                    row.className = "flight-row";


                    // Date formatting

                    const departure =
                        formatDateTime(flight.departureDateTime);

                    const arrival =
                        formatDateTime(flight.arrivalDateTime);


                    // Status class

                    let statusClass =
                        "bg-success-subtle text-success border-success-subtle";


                    if (flight.status === "CHECK_IN_STARTED") {

                        statusClass =
                            "bg-warning-subtle text-warning-emphasis border-warning-subtle";

                    }


                    if (flight.status === "CANCELLED") {

                        statusClass =
                            "bg-danger-subtle text-danger border-danger-subtle";

                    }


                    // =================================================
                    // ROW HTML
                    // =================================================

                    row.innerHTML = `

                        <td class="ps-4">

                            <div class="d-flex align-items-center">

                                <div class="bg-light rounded p-2 me-3">

                                    <i class="fas fa-plane text-navy"></i>

                                </div>

                                <div>

                                    <div class="flight-code text-navy">

                                        ${flight.flightCode}

                                    </div>

                                    <small class="text-muted">

                                        ${flight.flightId}

                                    </small>

                                </div>

                            </div>

                        </td>


                        <td>

                            <div class="d-flex flex-column">

                                <strong>

                                    ${flight.source.airportCode}

                                    <i class="fas fa-arrow-right mx-2 text-muted"></i>

                                    ${flight.destination.airportCode}

                                </strong>

                                <small class="text-muted">

                                    ${flight.source.city}
                                    → 
                                    ${flight.destination.city}

                                </small>

                            </div>

                        </td>


                        <td>

                            <div>

                                <strong>

                                    ${departure}

                                </strong>

                            </div>

                            <div class="small text-muted mt-1">

                                <i class="fas fa-plane-arrival me-1"></i>

                                Arrives: ${arrival}

                            </div>

                        </td>


                        <td>

                            <div>

                                <strong>

                                    ${flight.aircraft.model}

                                </strong>

                            </div>

                            <small class="text-muted">

                                Capacity:
                                ${flight.aircraft.capacity}

                            </small>

                        </td>


                        <td>

                            <span class="price-tag">

                                ₹${Number(flight.baseFare).toLocaleString("en-IN")}

                            </span>

                        </td>


                        <td>

                            <span class="badge rounded-pill border badge-status ${statusClass}">

                                ${formatStatus(flight.status)}

                            </span>

                        </td>


                        <td class="text-center pe-4">

                            <a href="${pageContext.request.contextPath}/bookings/new?flightId=${encodeURIComponent(flight.flightId)}"
                               class="btn btn-navy rounded-pill px-4 btn-sm">

                                <i class="fas fa-ticket-alt me-1"></i>

                                Book Now

                            </a>

                        </td>

                    `;


                    tableBody.appendChild(row);

                });

            })


            // =================================================
            // ERROR
            // =================================================

            .catch(error => {

                console.error("Flight search error:", error);


                document
                    .getElementById("flightTableContainer")
                    .classList.add("d-none");


                document
                    .getElementById("noFlightsAlert")
                    .classList.add("d-none");


                document
                    .getElementById("errorAlert")
                    .classList.remove("d-none");


                document
                    .getElementById("errorMessage")
                    .innerText =
                        "Unable to retrieve flight data. Please try again later.";

            });

    }


    // =========================================================
    // INVALID SEARCH
    // =========================================================

    else {

        document
            .getElementById("noFlightsAlert")
            .classList.remove("d-none");

        document
            .getElementById("noFlightsAlert")
            .querySelector("strong")
            .innerText = "Invalid search.";

    }


    // =========================================================
    // FORMAT DATE TIME
    // =========================================================

    function formatDateTime(dateTime) {

        if (!dateTime) {
            return "N/A";
        }

        const date = new Date(dateTime);

        return date.toLocaleString("en-IN", {

            day: "2-digit",

            month: "short",

            year: "numeric",

            hour: "2-digit",

            minute: "2-digit"

        });

    }


    // =========================================================
    // FORMAT STATUS
    // =========================================================

    function formatStatus(status) {

        if (!status) {
            return "UNKNOWN";
        }

        return status.replaceAll("_", " ");

    }

</script>

</body>
</html>