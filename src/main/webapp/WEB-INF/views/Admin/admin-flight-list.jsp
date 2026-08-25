<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>ABC Airline - Flights</title>


    <!-- Bootstrap 5 -->

    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
            rel="stylesheet">


    <style>

        /* =====================================================
           BODY
           ===================================================== */

        body {
            background-color: #f4f7fb;
            font-family: Arial, sans-serif;
        }


        /* =====================================================
           NAVBAR
           ===================================================== */

        .navbar {
            background-color: #071b3a;
        }


        .navbar-brand {
            color: white !important;
            font-size: 24px;
            font-weight: bold;
        }


        .navbar-text {
            color: white;
        }


        /* =====================================================
           MAIN CONTAINER
           ===================================================== */

        .page-container {
            max-width: 1200px;
            margin: 40px auto;
        }


        /* =====================================================
           PAGE HEADER
           ===================================================== */

        .page-title {
            color: #071b3a;
            font-weight: bold;
            margin-bottom: 5px;
        }


        .page-subtitle {
            color: #6c757d;
            margin-bottom: 0;
        }


        /* =====================================================
           BACK BUTTON
           ===================================================== */

        .btn-back {
            background-color: white;
            color: #071b3a;
            border: 1px solid #071b3a;
            padding: 8px 15px;
            border-radius: 5px;
            text-decoration: none;
            font-size: 14px;
            font-weight: 600;
        }


        .btn-back:hover {
            background-color: #071b3a;
            color: white;
        }


        /* =====================================================
           CONTENT CARD
           ===================================================== */

        .content-card {
            background-color: white;
            border-radius: 8px;
            padding: 25px;
            margin-top: 30px;

            box-shadow:
                    0 5px 20px
                    rgba(0, 0, 0, 0.08);
        }


        /* =====================================================
           SEARCH BOX
           ===================================================== */

        .search-box {
            max-width: 350px;
        }


        .search-label {
            color: #071b3a;
            font-weight: 600;
        }


        /* =====================================================
           TABLE
           ===================================================== */

        .table thead th {
            background-color: #071b3a;
            color: white;
            border: none;
            padding: 12px;
            white-space: nowrap;
        }


        .table tbody td {
            padding: 12px;
            vertical-align: middle;
        }


        .table tbody tr:hover {
            background-color: #f4f7fb;
        }


        /* =====================================================
           DETAILS BUTTON
           ===================================================== */

        .btn-details {
            background-color: #071b3a;
            color: white;
            border: none;
            padding: 6px 14px;
            border-radius: 5px;
            text-decoration: none;
            font-size: 13px;
            font-weight: 600;
        }


        .btn-details:hover {
            background-color: #0d2c5c;
            color: white;
        }


        /* =====================================================
           STATUS
           ===================================================== */

        .status-badge {
            display: inline-block;
            padding: 5px 10px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: bold;
        }


        .status-scheduled {
            background-color: #d1e7dd;
            color: #0f5132;
        }


        .status-other {
            background-color: #e2e3e5;
            color: #41464b;
        }


        /* =====================================================
           LOADING
           ===================================================== */

        .loading {
            text-align: center;
            color: #6c757d;
            padding: 30px;
        }


        /* =====================================================
           EMPTY
           ===================================================== */

        .empty-message {
            text-align: center;
            color: #6c757d;
            padding: 30px;
        }


        /* =====================================================
           LOGOUT
           ===================================================== */

        .logout-btn {
            border: 1px solid white;
            color: white;
            background: transparent;
            padding: 5px 15px;
            border-radius: 5px;
        }


        .logout-btn:hover {
            background-color: white;
            color: #071b3a;
        }

    </style>

</head>


<body>


<!-- =========================================================
     NAVBAR
     ========================================================= -->

<nav class="navbar navbar-dark">

    <div class="container">


        <a
                class="navbar-brand"
                href="${pageContext.request.contextPath}/admin/menu">

            ✈ ABC Airline

        </a>


        <div class="d-flex align-items-center gap-3">

            <span class="navbar-text">

                Admin

            </span>


            <button
                    type="button"
                    id="logoutButton"
                    class="logout-btn">

                Logout

            </button>

        </div>

    </div>

</nav>



<!-- =========================================================
     MAIN CONTENT
     ========================================================= -->

<div class="container page-container">


    <!-- =====================================================
         PAGE HEADER
         ===================================================== -->

    <div class="d-flex justify-content-between align-items-center">

        <div>

            <h2 class="page-title">

                Flights

            </h2>

            <p class="page-subtitle">

                View and manage airline flights.

            </p>

        </div>


        <a
                href="${pageContext.request.contextPath}/admin/menu"
                class="btn-back">

            ← Back to Admin Menu

        </a>

    </div>



    <!-- =====================================================
         FLIGHT CONTENT
         ===================================================== -->

    <div class="content-card">


        <!-- =================================================
             SEARCH
             ================================================= -->

        <div class="row align-items-end mb-4">

            <div class="col-md-6">

                <label
                        for="flightSearch"
                        class="form-label search-label">

                    Search Flight Code

                </label>


                <input
                        type="text"
                        id="flightSearch"
                        class="form-control search-box"
                        placeholder="Example: BOLR737">

            </div>


            <div class="col-md-6 text-md-end mt-3 mt-md-0">

                <span
                        id="flightCount"
                        class="text-muted">

                    Loading flights...

                </span>

            </div>

        </div>



        <!-- =================================================
             API MESSAGE
             ================================================= -->

        <div id="apiMessage"></div>



        <!-- =================================================
             TABLE
             ================================================= -->

        <div class="table-responsive">

            <table
                    class="table table-bordered table-hover mb-0">


                <thead>

                <tr>

                    <th>
                        #
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
                        Status
                    </th>

                    <th>
                        Action
                    </th>

                </tr>

                </thead>


                <tbody id="flightTableBody">

                <tr>

                    <td
                            colspan="7"
                            class="loading">

                        Loading flights...

                    </td>

                </tr>

                </tbody>


            </table>

        </div>


    </div>


</div>



<script>


    /* =========================================================
       CONTEXT PATH
       ========================================================= */

    const contextPath =
        "${pageContext.request.contextPath}";


    /* =========================================================
       API URL
       ========================================================= */

    const flightsApi =
        contextPath + "/api/v1/admin/flights/";


    /* =========================================================
       ELEMENTS
       ========================================================= */

    const flightTableBody =
        document.getElementById(
            "flightTableBody"
        );


    const flightSearch =
        document.getElementById(
            "flightSearch"
        );


    const flightCount =
        document.getElementById(
            "flightCount"
        );


    const apiMessage =
        document.getElementById(
            "apiMessage"
        );


    /* =========================================================
       STORE ALL FLIGHTS
       ========================================================= */

    let allFlights = [];



    /* =========================================================
       GET TOKEN
       ========================================================= */

    function getToken() {

        return localStorage.getItem("token");

    }



    /* =========================================================
       CHECK TOKEN
       ========================================================= */

    function checkToken() {

        const token =
            getToken();


        if (!token) {

            window.location.href =
                contextPath + "/login";

            return false;

        }


        return true;

    }



    /* =========================================================
       LOAD FLIGHTS
       ========================================================= */

    async function loadFlights() {


        /* -----------------------------------------------------
           CHECK TOKEN
           ----------------------------------------------------- */

        if (!checkToken()) {
            return;
        }


        /* -----------------------------------------------------
           LOADING
           ----------------------------------------------------- */

        flightTableBody.innerHTML = `

            <tr>

                <td
                        colspan="7"
                        class="loading">

                    Loading flights...

                </td>

            </tr>

        `;


        flightCount.innerText =
            "Loading flights...";


        try {


            /* -------------------------------------------------
               API CALL
               ------------------------------------------------- */

            const response =
                await fetch(
                    flightsApi,
                    {
                        method: "GET",

                        headers: {

                            "Authorization":
                                "Bearer " + getToken(),

                            "Content-Type":
                                "application/json"

                        }

                    }
                );


            /* -------------------------------------------------
               RESPONSE
               ------------------------------------------------- */

            const result =
                await response
                    .json()
                    .catch(function () {

                        return null;

                    });



            /* -------------------------------------------------
               SUCCESS
               ------------------------------------------------- */

            if (
                response.ok &&
                result &&
                result.status === "SUCCESS"
            ) {


                allFlights =
                    result.responseData || [];


                renderFlights(
                    allFlights
                );


                return;

            }



            /* -------------------------------------------------
               API ERROR
               ------------------------------------------------- */

            const errorMessage =
                result &&
                result.message
                    ? result.message
                    : "Unable to retrieve flights.";


            apiMessage.innerHTML = `

                <div class="alert alert-danger">

                    ${errorMessage}

                </div>

            `;


            flightTableBody.innerHTML = `

                <tr>

                    <td
                            colspan="7"
                            class="empty-message">

                        Unable to load flights.

                    </td>

                </tr>

            `;


            flightCount.innerText =
                "0 flights";


        } catch (error) {


            console.error(error);


            apiMessage.innerHTML = `

                <div class="alert alert-danger">

                    Unable to connect to the server.

                </div>

            `;


            flightTableBody.innerHTML = `

                <tr>

                    <td
                            colspan="7"
                            class="empty-message">

                        Unable to load flights.

                    </td>

                </tr>

            `;


            flightCount.innerText =
                "0 flights";

        }

    }



    /* =========================================================
       RENDER FLIGHTS
       ========================================================= */

    function renderFlights(
        flights
    ) {


        /* -----------------------------------------------------
           EMPTY
           ----------------------------------------------------- */

        if (
            !flights ||
            flights.length === 0
        ) {

            flightTableBody.innerHTML = `

                <tr>

                    <td
                            colspan="7"
                            class="empty-message">

                        No flights found.

                    </td>

                </tr>

            `;


            flightCount.innerText =
                "0 flights";


            return;

        }



        /* -----------------------------------------------------
           TABLE HTML
           ----------------------------------------------------- */

        let html = "";


        flights.forEach(
            function (
                flight,
                index
            ) {


                /* ---------------------------------------------
                   DATE FORMAT
                   --------------------------------------------- */

                const departure =
                    formatDateTime(
                        flight.departureDateTime
                    );


                /* ---------------------------------------------
                   STATUS CLASS
                   --------------------------------------------- */

                let statusClass =
                    "status-other";


                if (
                    flight.status ===
                    "SCHEDULED"
                ) {

                    statusClass =
                        "status-scheduled";

                }



                /* ---------------------------------------------
                   CREATE ROW
                   --------------------------------------------- */

                html += `

                    <tr>

                        <td>
                            ${index + 1}
                        </td>


                        <td>

                            <strong>
                                ${flight.flightCode}
                            </strong>

                        </td>


                        <td>

                            ${flight.source.airportCode}

                            <br>

                            <small class="text-muted">

                                ${flight.source.city}

                            </small>

                        </td>


                        <td>

                            ${flight.destination.airportCode}

                            <br>

                            <small class="text-muted">

                                ${flight.destination.city}

                            </small>

                        </td>


                        <td>

                            ${departure}

                        </td>


                        <td>

                            <span
                                    class="status-badge ${statusClass}">

                                ${flight.status}

                            </span>

                        </td>


                        <td>

                            <a
                                    href="${contextPath}/admin/flights/${flight.flightId}"
                                    class="btn-details">

                                Details

                            </a>

                        </td>

                    </tr>

                `;

            }
        );


        flightTableBody.innerHTML =
            html;


        flightCount.innerText =
            flights.length +
            (
                flights.length === 1
                    ? " flight"
                    : " flights"
            );

    }



    /* =========================================================
       FORMAT DATE TIME
       ========================================================= */

    function formatDateTime(
        dateTime
    ) {

        if (!dateTime) {
            return "-";
        }


        const date =
            new Date(dateTime);


        if (isNaN(date.getTime())) {
            return dateTime;
        }


        return date.toLocaleString(
            "en-IN",
            {
                day: "2-digit",

                month: "short",

                year: "numeric",

                hour: "2-digit",

                minute: "2-digit"
            }
        );

    }



    /* =========================================================
       FRONTEND SEARCH
       =========================================================

       No API call.

       Search happens only against
       allFlights already retrieved.
       ========================================================= */

    flightSearch.addEventListener(
        "input",
        function () {


            const searchValue =
                this.value
                    .trim()
                    .toLowerCase();


            /* ---------------------------------------------
               EMPTY SEARCH
               --------------------------------------------- */

            if (searchValue === "") {

                renderFlights(
                    allFlights
                );

                return;

            }


            /* ---------------------------------------------
               FILTER BY FLIGHT CODE
               --------------------------------------------- */

            const filteredFlights =
                allFlights.filter(
                    function (flight) {

                        return flight.flightCode
                            .toLowerCase()
                            .includes(searchValue);

                    }
                );


            /* ---------------------------------------------
               DISPLAY FILTERED RESULTS
               --------------------------------------------- */

            renderFlights(
                filteredFlights
            );

        }
    );



    /* =========================================================
       LOGOUT
       ========================================================= */

    document
        .getElementById(
            "logoutButton"
        )
        .addEventListener(
            "click",
            function () {


                localStorage.removeItem(
                    "token"
                );


                localStorage.removeItem(
                    "role"
                );


                window.location.href =
                    contextPath + "/login";

            }
        );



    /* =========================================================
       LOAD FLIGHTS WHEN PAGE OPENS
       ========================================================= */

    document.addEventListener(
        "DOMContentLoaded",
        function () {

            loadFlights();

        }
    );


</script>


</body>

</html>