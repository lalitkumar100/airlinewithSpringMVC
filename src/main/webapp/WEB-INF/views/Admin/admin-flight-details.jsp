<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>ABC Airline - Flight Details</title>


    <!-- Bootstrap -->

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
            max-width: 1100px;
            margin: 40px auto;
        }


        /* =====================================================
           PAGE TITLE
           ===================================================== */

        .page-title {
            color: #071b3a;
            font-weight: bold;
            margin-bottom: 5px;
        }


        .page-subtitle {
            color: #6c757d;
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
           MAIN CARD
           ===================================================== */

        .details-card {
            background-color: white;
            border-radius: 8px;
            padding: 30px;
            margin-top: 30px;

            box-shadow:
                    0 5px 20px
                    rgba(0, 0, 0, 0.08);
        }


        /* =====================================================
           FLIGHT HEADER
           ===================================================== */

        .flight-code {
            color: #071b3a;
            font-size: 26px;
            font-weight: bold;
        }


        /* =====================================================
           INFORMATION BOX
           ===================================================== */

        .info-box {
            background-color: #f8f9fa;
            border-radius: 6px;
            padding: 18px;
            height: 100%;
        }


        .info-label {
            color: #6c757d;
            font-size: 13px;
            margin-bottom: 5px;
        }


        .info-value {
            color: #071b3a;
            font-weight: 600;
            font-size: 16px;
        }


        /* =====================================================
           SECTION TITLE
           ===================================================== */

        .section-title {
            color: #071b3a;
            font-weight: bold;
            margin-bottom: 20px;
        }


        /* =====================================================
           STATUS
           ===================================================== */

        .status-badge {
            display: inline-block;
            padding: 6px 12px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: bold;
        }


        .status-scheduled {
            background-color: #d1e7dd;
            color: #0f5132;
        }


        .status-cancelled {
            background-color: #f8d7da;
            color: #842029;
        }


        .status-other {
            background-color: #e2e3e5;
            color: #41464b;
        }


        /* =====================================================
           ACTION BUTTONS
           ===================================================== */

        .action-btn {
            min-width: 130px;
            padding: 10px 18px;
            border-radius: 5px;
            font-weight: 600;
            border: none;
        }


        .btn-revenue {
            background-color: #071b3a;
            color: white;
        }


        .btn-revenue:hover {
            background-color: #0d2c5c;
            color: white;
        }


        .btn-booking {
            background-color: #071b3a;
            color: white;
        }


        .btn-booking:hover {
            background-color: #0d2c5c;
            color: white;
        }


        .btn-cancel-flight {
            background-color: #dc3545;
            color: white;
        }


        .btn-cancel-flight:hover {
            background-color: #bb2d3b;
            color: white;
        }


        /* =====================================================
           LOADING
           ===================================================== */

        .loading {
            text-align: center;
            color: #6c757d;
            padding: 50px;
        }


        /* =====================================================
           ERROR
           ===================================================== */

        .error-container {
            margin-top: 30px;
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

                Flight Details

            </h2>

            <p class="page-subtitle mb-0">

                View complete flight information.

            </p>

        </div>


        <a
                href="${pageContext.request.contextPath}/admin/flights"
                class="btn-back">

            ← Back to Flights

        </a>

    </div>



    <!-- =====================================================
         API MESSAGE
         ===================================================== -->

    <div
            id="apiMessage"
            class="error-container">

    </div>



    <!-- =====================================================
         LOADING
         ===================================================== -->

    <div
            id="loading"
            class="details-card loading">

        Loading flight details...

    </div>



    <!-- =====================================================
         FLIGHT DETAILS
         ===================================================== -->

    <div
            id="flightDetails"
            class="details-card d-none">


        <!-- =================================================
             FLIGHT HEADER
             ================================================= -->

        <div class="d-flex justify-content-between
                    align-items-center mb-4">

            <div>

                <div
                        id="flightCode"
                        class="flight-code">

                </div>

                <small
                        id="flightId"
                        class="text-muted">

                </small>

            </div>


            <div id="flightStatus">

            </div>

        </div>



        <!-- =================================================
             ROUTE
             ================================================= -->

        <h5 class="section-title">

            Flight Route

        </h5>


        <div class="row g-3 mb-4">


            <!-- SOURCE -->

            <div class="col-md-6">

                <div class="info-box">

                    <div class="info-label">

                        Source

                    </div>


                    <div
                            id="sourceAirport"
                            class="info-value">

                    </div>


                    <div
                            id="sourceName"
                            class="text-muted mt-1">

                    </div>


                    <div
                            id="sourceCity"
                            class="text-muted">

                    </div>

                </div>

            </div>



            <!-- DESTINATION -->

            <div class="col-md-6">

                <div class="info-box">

                    <div class="info-label">

                        Destination

                    </div>


                    <div
                            id="destinationAirport"
                            class="info-value">

                    </div>


                    <div
                            id="destinationName"
                            class="text-muted mt-1">

                    </div>


                    <div
                            id="destinationCity"
                            class="text-muted">

                    </div>

                </div>

            </div>


        </div>



        <!-- =================================================
             DATE / TIME
             ================================================= -->

        <h5 class="section-title">

            Schedule

        </h5>


        <div class="row g-3 mb-4">


            <div class="col-md-6">

                <div class="info-box">

                    <div class="info-label">

                        Departure

                    </div>


                    <div
                            id="departureDateTime"
                            class="info-value">

                    </div>

                </div>

            </div>


            <div class="col-md-6">

                <div class="info-box">

                    <div class="info-label">

                        Arrival

                    </div>


                    <div
                            id="arrivalDateTime"
                            class="info-value">

                    </div>

                </div>

            </div>


        </div>



        <!-- =================================================
             AIRCRAFT
             ================================================= -->

        <h5 class="section-title">

            Aircraft

        </h5>


        <div class="row g-3 mb-4">


            <div class="col-md-4">

                <div class="info-box">

                    <div class="info-label">

                        Aircraft ID

                    </div>


                    <div
                            id="aircraftId"
                            class="info-value">

                    </div>

                </div>

            </div>


            <div class="col-md-5">

                <div class="info-box">

                    <div class="info-label">

                        Model

                    </div>


                    <div
                            id="aircraftModel"
                            class="info-value">

                    </div>

                </div>

            </div>


            <div class="col-md-3">

                <div class="info-box">

                    <div class="info-label">

                        Capacity

                    </div>


                    <div
                            id="aircraftCapacity"
                            class="info-value">

                    </div>

                </div>

            </div>


        </div>



        <!-- =================================================
             FARE
             ================================================= -->

        <h5 class="section-title">

            Fare

        </h5>


        <div class="row g-3 mb-4">


            <div class="col-md-4">

                <div class="info-box">

                    <div class="info-label">

                        Base Fare

                    </div>


                    <div
                            id="baseFare"
                            class="info-value">

                    </div>

                </div>

            </div>


        </div>



        <!-- =================================================
             ACTIONS
             ================================================= -->

        <h5 class="section-title">

            Actions

        </h5>


        <div class="d-flex gap-3 flex-wrap">


            <!-- REVENUE -->

            <button
                    type="button"
                    id="revenueButton"
                    class="action-btn btn-revenue">

                Revenue

            </button>


            <!-- BOOKINGS -->

            <button
                    type="button"
                    id="bookingButton"
                    class="action-btn btn-booking">

                Bookings

            </button>


            <!-- CANCEL -->

            <button
                    type="button"
                    id="cancelButton"
                    class="action-btn btn-cancel-flight">

                Cancel Flight

            </button>


        </div>


    </div>


</div>



<!-- =========================================================
     CANCEL FLIGHT MODAL
     ========================================================= -->

<div
        class="modal fade"
        id="cancelFlightModal"
        tabindex="-1"
        aria-hidden="true">


    <div
            class="modal-dialog modal-dialog-centered">


        <div class="modal-content">


            <!-- HEADER -->

            <div class="modal-header">

                <h5 class="modal-title">

                    Cancel Flight

                </h5>


                <button
                        type="button"
                        class="btn-close"
                        data-bs-dismiss="modal"
                        aria-label="Close">

                </button>

            </div>



            <!-- BODY -->

            <div class="modal-body">


                <div class="alert alert-warning">

                    Are you sure you want to cancel this flight?

                </div>


                <p class="text-muted">

                    Enter your 8-character password to confirm
                    cancellation.

                </p>


                <label
                        for="cancelPassword"
                        class="form-label">

                    Password

                </label>


                <input
                        type="password"
                        id="cancelPassword"
                        class="form-control"
                        maxlength="8"
                        minlength="8"
                        placeholder="Enter 8-character password"
                        autocomplete="off">


                <div
                        id="passwordError"
                        class="text-danger mt-2">

                </div>


            </div>



            <!-- FOOTER -->

            <div class="modal-footer">


                <button
                        type="button"
                        class="btn btn-secondary"
                        data-bs-dismiss="modal">

                    Close

                </button>


                <button
                        type="button"
                        id="confirmCancelButton"
                        class="btn btn-danger">

                    Confirm Cancellation

                </button>


            </div>

        </div>

    </div>

</div>



<!-- Bootstrap JS -->

<script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js">
</script>



<script>


    /* =========================================================
       CONTEXT PATH
       ========================================================= */

    const contextPath =
        "${pageContext.request.contextPath}";


    /* =========================================================
       FLIGHT ID FROM URL
       ========================================================= */

    const pathParts =
        window.location.pathname
            .split("/")
            .filter(
                function (part) {
                    return part.length > 0;
                }
            );


    /*
     * Last part of URL is flight ID.
     *
     * Example:
     *
     * /airline/admin/flights/FLT933393
     *
     * flightId = FLT933393
     */

    const flightId =
        pathParts[pathParts.length - 1];



    /* =========================================================
       API URL
       ========================================================= */

    const flightApi =
        contextPath +
        "/api/v1/admin/flights/" +
        encodeURIComponent(flightId);



    /* =========================================================
       CANCEL API
       =========================================================

       Put your actual cancel endpoint here when available.

       Example only:

       const cancelApi =
           contextPath +
           "/api/v1/admin/flights/" +
           flightId +
           "/cancel";

       ========================================================= */

    const cancelApi = "";



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
       FORMAT DATE
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
       FORMAT MONEY
       ========================================================= */

    function formatMoney(
        amount
    ) {

        if (
            amount === null ||
            amount === undefined
        ) {

            return "-";

        }


        return "₹ " +
            Number(amount)
                .toLocaleString("en-IN");

    }



    /* =========================================================
       LOAD FLIGHT
       ========================================================= */

    async function loadFlight() {


        if (!checkToken()) {
            return;
        }


        try {


            const response =
                await fetch(
                    flightApi,
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


            const result =
                await response
                    .json()
                    .catch(
                        function () {
                            return null;
                        }
                    );



            /* =================================================
               SUCCESS
               ================================================= */

            if (
                response.ok &&
                result &&
                result.status === "SUCCESS"
            ) {


                displayFlight(
                    result.responseData
                );


                return;

            }



            /* =================================================
               ERROR
               ================================================= */

            const message =
                result &&
                result.message
                    ? result.message
                    : "Unable to retrieve flight.";


            showError(
                message
            );


        } catch (error) {


            console.error(error);


            showError(
                "Unable to connect to the server."
            );

        }

    }



    /* =========================================================
       DISPLAY FLIGHT
       ========================================================= */

    function displayFlight(
        flight
    ) {


        /* -----------------------------------------------------
           HEADER
           ----------------------------------------------------- */

        document.getElementById(
            "flightCode"
        ).innerText =
            flight.flightCode;


        document.getElementById(
            "flightId"
        ).innerText =
            "Flight ID: " +
            flight.flightId;



        /* -----------------------------------------------------
           STATUS
           ----------------------------------------------------- */

        let statusClass =
            "status-other";


        if (
            flight.status ===
            "SCHEDULED"
        ) {

            statusClass =
                "status-scheduled";

        }


        if (
            flight.status ===
            "CANCELLED"
        ) {

            statusClass =
                "status-cancelled";

        }


        document.getElementById(
            "flightStatus"
        ).innerHTML = `

            <span class="status-badge ${statusClass}">

                ${flight.status}

            </span>

        `;



        /* -----------------------------------------------------
           SOURCE
           ----------------------------------------------------- */

        document.getElementById(
            "sourceAirport"
        ).innerText =
            flight.source.airportCode;


        document.getElementById(
            "sourceName"
        ).innerText =
            flight.source.airportName;


        document.getElementById(
            "sourceCity"
        ).innerText =
            flight.source.city;



        /* -----------------------------------------------------
           DESTINATION
           ----------------------------------------------------- */

        document.getElementById(
            "destinationAirport"
        ).innerText =
            flight.destination.airportCode;


        document.getElementById(
            "destinationName"
        ).innerText =
            flight.destination.airportName;


        document.getElementById(
            "destinationCity"
        ).innerText =
            flight.destination.city;



        /* -----------------------------------------------------
           SCHEDULE
           ----------------------------------------------------- */

        document.getElementById(
            "departureDateTime"
        ).innerText =
            formatDateTime(
                flight.departureDateTime
            );


        document.getElementById(
            "arrivalDateTime"
        ).innerText =
            formatDateTime(
                flight.arrivalDateTime
            );



        /* -----------------------------------------------------
           AIRCRAFT
           ----------------------------------------------------- */

        document.getElementById(
            "aircraftId"
        ).innerText =
            flight.aircraft.aircraftId;


        document.getElementById(
            "aircraftModel"
        ).innerText =
            flight.aircraft.model;


        document.getElementById(
            "aircraftCapacity"
        ).innerText =
            flight.aircraft.capacity;



        /* -----------------------------------------------------
           FARE
           ----------------------------------------------------- */

        document.getElementById(
            "baseFare"
        ).innerText =
            formatMoney(
                flight.baseFare
            );



        /* -----------------------------------------------------
           SHOW PAGE
           ----------------------------------------------------- */

        document.getElementById(
            "loading"
        ).classList.add("d-none");


        document.getElementById(
            "flightDetails"
        ).classList.remove("d-none");

    }



    /* =========================================================
       SHOW ERROR
       ========================================================= */

    function showError(
        message
    ) {


        document.getElementById(
            "loading"
        ).classList.add("d-none");


        document.getElementById(
            "flightDetails"
        ).classList.add("d-none");


        document.getElementById(
            "apiMessage"
        ).innerHTML = `

            <div class="alert alert-danger">

                ${message}

            </div>

        `;

    }



    /* =========================================================
       REVENUE BUTTON
       ========================================================= */

    document.getElementById(
        "revenueButton"
    ).addEventListener(
        "click",
        function () {


            window.location.href =
                contextPath +
                "/admin/flights/" +
                encodeURIComponent(
                    flightId
                ) +
                "/revenue";

        }
    );



    /* =========================================================
       BOOKINGS BUTTON
       ========================================================= */

    document.getElementById(
        "bookingButton"
    ).addEventListener(
        "click",
        function () {


            window.location.href =
                contextPath +
                "/admin/flights/" +
                encodeURIComponent(
                    flightId
                ) +
                "/bookings";

        }
    );



    /* =========================================================
       OPEN CANCEL MODAL
       ========================================================= */

    const cancelModal =
        new bootstrap.Modal(
            document.getElementById(
                "cancelFlightModal"
            )
        );


    document.getElementById(
        "cancelButton"
    ).addEventListener(
        "click",
        function () {


            document.getElementById(
                "cancelPassword"
            ).value = "";


            document.getElementById(
                "passwordError"
            ).innerText = "";


            cancelModal.show();

        }
    );



    /* =========================================================
       PASSWORD VALIDATION
       ========================================================= */

    document.getElementById(
        "confirmCancelButton"
    ).addEventListener(
        "click",
        async function () {


            const password =
                document.getElementById(
                    "cancelPassword"
                ).value;


            const passwordError =
                document.getElementById(
                    "passwordError"
                );


            /* -------------------------------------------------
               REQUIRED
               ------------------------------------------------- */

            if (!password) {

                passwordError.innerText =
                    "Password is required.";

                return;

            }


            /* -------------------------------------------------
               EXACTLY 8 CHARACTERS
               ------------------------------------------------- */

            if (password.length !== 8) {

                passwordError.innerText =
                    "Password must be exactly 8 characters.";

                return;

            }


            /* -------------------------------------------------
               CANCEL API NOT PROVIDED YET
               ------------------------------------------------- */

            if (!cancelApi) {

                passwordError.innerText =
                    "Cancel API is not configured yet.";

                return;

            }


            try {


                const response =
                    await fetch(
                        cancelApi,
                        {
                            method: "POST",

                            headers: {

                                "Authorization":
                                    "Bearer " + getToken(),

                                "Content-Type":
                                    "application/json"

                            },

                            body: JSON.stringify({

                                password: password

                            })

                        }
                    );


                const result =
                    await response
                        .json()
                        .catch(
                            function () {
                                return null;
                            }
                        );



                /* ------------------------------------------------
                   SUCCESS
                   ------------------------------------------------ */

                if (
                    response.ok &&
                    result &&
                    (
                        result.status ===
                        "SUCCESS" ||
                        response.status === 200 ||
                        response.status === 201
                    )
                ) {


                    cancelModal.hide();


                    document.getElementById(
                        "apiMessage"
                    ).innerHTML = `

                        <div class="alert alert-success">

                            ${result.message || "Flight cancelled successfully."}

                        </div>

                    `;


                    /*
                     * Reload flight details so that
                     * status becomes CANCELLED.
                     */

                    loadFlight();


                    return;

                }



                /* ------------------------------------------------
                   API ERROR
                   ------------------------------------------------ */

                passwordError.innerText =
                    result &&
                    result.message
                        ? result.message
                        : "Unable to cancel flight.";


            } catch (error) {


                console.error(error);


                passwordError.innerText =
                    "Unable to connect to the server.";

            }

        }
    );



    /* =========================================================
       LOGOUT
       ========================================================= */

    document.getElementById(
        "logoutButton"
    ).addEventListener(
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
       LOAD PAGE
       ========================================================= */

    document.addEventListener(
        "DOMContentLoaded",
        function () {

            loadFlight();

        }
    );


</script>


</body>

</html>