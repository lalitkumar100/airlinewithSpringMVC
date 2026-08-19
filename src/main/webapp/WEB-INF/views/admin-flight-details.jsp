<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1">

    <title>Flight Details - Admin</title>

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
            --blue: #0d6efd;
            --light-blue: #eaf3ff;
            --light-gray: #f5f8fc;
        }


        body {
            background-color: var(--light-gray);
            font-family: 'Segoe UI',
                         Tahoma,
                         Geneva,
                         Verdana,
                         sans-serif;
        }


        .page-header {

            background: linear-gradient(
                    135deg,
                    var(--navy),
                    #0056a6
            );

            color: white;

            padding: 35px 0 55px;

            margin-bottom: -25px;
        }


        .page-header h2 {
            font-weight: 700;
        }


        .main-card {

            border: none;

            border-radius: 16px;

            box-shadow:
                0 8px 30px
                rgba(0, 51, 102, 0.12);

            overflow: hidden;
        }


        .card-header-blue {

            background-color: var(--navy);

            color: white;

            padding: 18px 24px;
        }


        .info-card {

            border: 1px solid #e2eaf3;

            border-radius: 12px;

            background: white;

            height: 100%;
        }


        .info-label {

            color: #6c757d;

            font-size: 0.78rem;

            font-weight: 600;

            text-transform: uppercase;

            letter-spacing: 0.4px;

            margin-bottom: 4px;
        }


        .info-value {

            color: #212529;

            font-size: 1rem;

            font-weight: 600;
        }


        .flight-code {

            background-color: var(--light-blue);

            color: var(--navy);

            padding: 6px 12px;

            border-radius: 6px;

            font-weight: 700;
        }


        .airport-code {

            color: var(--navy);

            font-size: 1.5rem;

            font-weight: 800;
        }


        .airport-name {

            font-weight: 600;

            color: #495057;
        }


        .route-line {

            height: 2px;

            background-color: #cbd5e1;

            position: relative;

            margin: 0 15px;
        }


        .route-plane {

            color: var(--blue);

            font-size: 1.3rem;
        }


        .status-badge {

            padding: 7px 15px;

            border-radius: 20px;

            font-weight: 600;
        }


        .section-title {

            color: var(--navy);

            font-weight: 700;
        }


        .loading-container {

            min-height: 400px;

            display: flex;

            align-items: center;

            justify-content: center;
        }


        .action-btn {

            min-width: 120px;
        }

    </style>

</head>


<body>


<!-- ===================================================== -->
<!-- PAGE HEADER -->
<!-- ===================================================== -->

<div class="page-header">

    <div class="container">

        <div class="d-flex
                    justify-content-between
                    align-items-center">

            <div>

                <h2 class="mb-1">

                    <i class="fas fa-plane me-2"></i>

                    Flight Details

                </h2>

                <p class="mb-0 opacity-75">

                    Administrator Flight Management

                </p>

            </div>


            <a href="${pageContext.request.contextPath}/admin/flights"
               class="btn btn-light">

                <i class="fas fa-arrow-left me-1"></i>

                Back to Flights

            </a>

        </div>

    </div>

</div>



<!-- ===================================================== -->
<!-- MAIN CONTENT -->
<!-- ===================================================== -->

<div class="container py-4">


    <!-- ERROR -->

    <div id="errorMessage"
         class="alert alert-danger d-none shadow-sm">

        <i class="fas fa-circle-exclamation me-2"></i>

        <span id="errorText"></span>

    </div>


    <!-- SUCCESS -->

    <div id="successMessage"
         class="alert alert-success d-none shadow-sm">

        <i class="fas fa-circle-check me-2"></i>

        <span id="successText"></span>

    </div>


    <!-- LOADING -->

    <div id="loading"
         class="card main-card loading-container">

        <div class="text-center">

            <div class="spinner-border text-primary"
                 style="width: 3rem; height: 3rem;"
                 role="status">

            </div>

            <p class="text-muted mt-3 mb-0">

                Loading flight details...

            </p>

        </div>

    </div>



    <!-- ================================================= -->
    <!-- FLIGHT CONTENT -->
    <!-- ================================================= -->

    <div id="flightContent"
         class="d-none">


        <!-- ================================================= -->
        <!-- FLIGHT HEADER -->
        <!-- ================================================= -->

        <div class="card main-card mb-4">

            <div class="card-header-blue">

                <div class="row align-items-center">

                    <div class="col-md-8">

                        <div class="small opacity-75 mb-2">

                            Flight Code

                        </div>

                        <h3 class="mb-0">

                            <span id="flightCode"
                                  class="flight-code">

                            </span>

                        </h3>

                    </div>


                    <div class="col-md-4
                                text-md-end
                                mt-3
                                mt-md-0">

                        <span id="flightStatus"
                              class="status-badge">

                        </span>

                    </div>

                </div>

            </div>


            <div class="card-body p-4">


                <!-- ROUTE -->

                <div class="row
                            align-items-center
                            text-center">


                    <!-- SOURCE -->

                    <div class="col-md-4">

                        <div id="sourceCode"
                             class="airport-code">

                        </div>

                        <div id="sourceCity"
                             class="airport-name">

                        </div>

                        <small id="sourceAirport"
                               class="text-muted d-block">

                        </small>


                        <div class="mt-3">

                            <span class="badge
                                         bg-light
                                         text-dark
                                         border">

                                <i class="far fa-clock me-1"></i>

                                <span id="departureTime"></span>

                            </span>

                        </div>

                    </div>



                    <!-- ROUTE -->

                    <div class="col-md-4
                                my-4
                                my-md-0">

                        <div class="small text-muted mb-2">

                            <span id="flightRouteCode"></span>

                        </div>


                        <div class="d-flex
                                    align-items-center">

                            <div class="route-line
                                        flex-grow-1">
                            </div>

                            <i class="fas fa-plane
                                      route-plane
                                      mx-2">
                            </i>

                            <div class="route-line
                                        flex-grow-1">
                            </div>

                        </div>


                        <div class="small
                                    text-muted
                                    mt-2">

                            <span id="aircraftModel"></span>

                        </div>

                    </div>



                    <!-- DESTINATION -->

                    <div class="col-md-4">

                        <div id="destinationCode"
                             class="airport-code">

                        </div>

                        <div id="destinationCity"
                             class="airport-name">

                        </div>

                        <small id="destinationAirport"
                               class="text-muted d-block">

                        </small>


                        <div class="mt-3">

                            <span class="badge
                                         bg-light
                                         text-dark
                                         border">

                                <i class="far fa-clock me-1"></i>

                                <span id="arrivalTime"></span>

                            </span>

                        </div>

                    </div>

                </div>

            </div>

        </div>



        <!-- ================================================= -->
        <!-- FLIGHT INFORMATION -->
        <!-- ================================================= -->

        <div class="card main-card mb-4">

            <div class="card-header
                        bg-white
                        border-0
                        pt-4
                        px-4">

                <h5 class="section-title mb-0">

                    <i class="fas fa-circle-info me-2"></i>

                    Flight Information

                </h5>

            </div>


            <div class="card-body p-4">

                <div class="row g-3">


                    <!-- Flight ID -->

                    <div class="col-md-4">

                        <div class="info-card p-3">

                            <div class="info-label">
                                Flight ID
                            </div>

                            <div id="flightId"
                                 class="info-value
                                        font-monospace">
                            </div>

                        </div>

                    </div>



                    <!-- Flight Code -->

                    <div class="col-md-4">

                        <div class="info-card p-3">

                            <div class="info-label">
                                Flight Code
                            </div>

                            <div id="infoFlightCode"
                                 class="info-value">
                            </div>

                        </div>

                    </div>



                    <!-- Base Fare -->

                    <div class="col-md-4">

                        <div class="info-card p-3">

                            <div class="info-label">
                                Base Fare
                            </div>

                            <div id="baseFare"
                                 class="info-value text-success">
                            </div>

                        </div>

                    </div>



                    <!-- Aircraft ID -->

                    <div class="col-md-4">

                        <div class="info-card p-3">

                            <div class="info-label">
                                Aircraft ID
                            </div>

                            <div id="aircraftId"
                                 class="info-value
                                        font-monospace">
                            </div>

                        </div>

                    </div>



                    <!-- Aircraft Model -->

                    <div class="col-md-4">

                        <div class="info-card p-3">

                            <div class="info-label">
                                Aircraft Model
                            </div>

                            <div id="infoAircraftModel"
                                 class="info-value">
                            </div>

                        </div>

                    </div>



                    <!-- Capacity -->

                    <div class="col-md-4">

                        <div class="info-card p-3">

                            <div class="info-label">
                                Aircraft Capacity
                            </div>

                            <div id="capacity"
                                 class="info-value">
                            </div>

                        </div>

                    </div>



                    <!-- Total Bookings -->

                    <div class="col-md-4">

                        <div class="info-card p-3">

                            <div class="info-label">
                                Total Bookings
                            </div>

                            <div id="totalBookings"
                                 class="info-value text-primary">
                            </div>

                        </div>

                    </div>



                    <!-- Created -->

                    <div class="col-md-4">

                        <div class="info-card p-3">

                            <div class="info-label">
                                Created At
                            </div>

                            <div id="createdAt"
                                 class="info-value">
                            </div>

                        </div>

                    </div>



                    <!-- Updated -->

                    <div class="col-md-4">

                        <div class="info-card p-3">

                            <div class="info-label">
                                Last Updated
                            </div>

                            <div id="updatedAt"
                                 class="info-value">
                            </div>

                        </div>

                    </div>

                </div>

            </div>

        </div>



        <!-- ================================================= -->
        <!-- ADMIN ACTIONS -->
        <!-- ================================================= -->

        <div class="card main-card mb-4">

            <div class="card-header
                        bg-white
                        border-0
                        pt-4
                        px-4">

                <h5 class="section-title mb-0">

                    <i class="fas fa-sliders me-2"></i>

                    Admin Actions

                </h5>

            </div>


            <div class="card-body p-4">

                <div class="d-flex flex-wrap gap-2">


                    <!-- BOOKINGS -->

                    <button type="button"
                            class="btn btn-primary action-btn"
                            onclick="viewBookings()">

                        <i class="fas fa-users me-1"></i>

                        Bookings

                    </button>



                    <!-- REVENUE -->

                    <button type="button"
                            class="btn btn-outline-primary action-btn"
                            onclick="viewRevenue()">

                        <i class="fas fa-chart-line me-1"></i>

                        Revenue

                    </button>



                    <!-- TICKETS -->

                    <button type="button"
                            class="btn btn-outline-primary action-btn"
                            onclick="viewTickets()">

                        <i class="fas fa-ticket me-1"></i>

                        Tickets

                    </button>



                    <!-- CANCEL -->

                    <button type="button"
                            id="cancelButton"
                            class="btn btn-outline-danger action-btn"
                            onclick="cancelFlight()">

                        <i class="fas fa-ban me-1"></i>

                        Cancel Flight

                    </button>

                </div>

            </div>

        </div>


    </div>

</div>



<!-- ===================================================== -->
<!-- FOOTER -->
<!-- ===================================================== -->

<div class="container text-center py-4">

    <p class="text-muted small mb-0">

        &copy; 2026 Airline Management System |

        <i class="fas fa-shield-alt"></i>

        Administrator Portal

    </p>

</div>



<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js">
</script>



<script>

    /*
     * =====================================================
     * IMPORTANT
     *
     * Controller sends:
     *
     * model.addAttribute("flightId", flightId);
     *
     * Therefore we use:
     *
     * ${flightId}
     *
     * NOT:
     *
     * ${param.flightId}
     * =====================================================
     */


    const FLIGHT_ID = "${flightId}";

    const CONTEXT_PATH =
        '${pageContext.request.contextPath}';



    /*
     * =====================================================
     * PAGE INITIALIZATION
     * =====================================================
     */

    document.addEventListener(
        "DOMContentLoaded",
        function () {

            console.log(
                "Flight ID:",
                FLIGHT_ID
            );


            if (
                !FLIGHT_ID ||
                FLIGHT_ID.trim() === ""
            ) {

                showError(
                    "No Flight ID was provided."
                );

                return;
            }


            loadFlightDetails(
                FLIGHT_ID
            );

        }
    );



    /*
     * =====================================================
     * GET JWT TOKEN
     * =====================================================
     */

    function getToken() {

        const token =
            localStorage.getItem(
                "jwtToken"
            );


        if (!token) {

            redirectToLogin();

            return null;
        }


        return token;

    }



    /*
     * =====================================================
     * REDIRECT TO LOGIN
     * =====================================================
     */

    function redirectToLogin() {

        localStorage.removeItem(
            "jwtToken"
        );


        window.location.href =
            CONTEXT_PATH +
            "/users/login";

    }



    /*
     * =====================================================
     * LOAD FLIGHT DETAILS
     *
     * GET
     *
     * /api/v1/admin/flights/{flightId}
     * =====================================================
     */

    function loadFlightDetails(
        flightId
    ) {

        const token =
            getToken();


        if (!token) {

            return;
        }


        const apiUrl =
            CONTEXT_PATH +
            "/api/v1/admin/flights/" +
            encodeURIComponent(
                flightId
            );


        console.log(
            "Calling API:",
            apiUrl
        );


        fetch(
            apiUrl,
            {

                method: "GET",

                headers: {

                    "Accept":
                        "application/json",

                    "Authorization":
                        "Bearer " + token

                }

            }
        )

        .then(
            function (response) {


                /*
                 * UNAUTHORIZED
                 */

                if (
                    response.status === 401 ||
                    response.status === 403
                ) {

                    redirectToLogin();

                    throw new Error(
                        "Session expired. Please login again."
                    );

                }


                /*
                 * OTHER ERROR
                 */

                if (!response.ok) {

                    if (
                        response.status === 404
                    ) {

                        throw new Error(
                            "Flight not found."
                        );

                    }


                    throw new Error(
                        "Unable to load flight details. HTTP Status: " +
                        response.status
                    );

                }


                return response.json();

            }
        )

        .then(
            function (flight) {

                console.log(
                    "Flight Response:",
                    flight
                );


                renderFlight(
                    flight
                );

            }
        )

        .catch(
            function (error) {

                console.error(
                    "Flight Error:",
                    error
                );


                showError(
                    error.message ||
                    "Failed to load flight details."
                );

            }
        );

    }



    /*
     * =====================================================
     * RENDER FLIGHT
     * =====================================================
     */

    function renderFlight(
        flight
    ) {


        /*
         * FLIGHT ID
         */

        document
            .getElementById(
                "flightId"
            )
            .innerText =
            flight.flightId ||
            "-";


        /*
         * FLIGHT CODE
         */

        document
            .getElementById(
                "flightCode"
            )
            .innerText =
            flight.flightCode ||
            "-";


        document
            .getElementById(
                "infoFlightCode"
            )
            .innerText =
            flight.flightCode ||
            "-";


        document
            .getElementById(
                "flightRouteCode"
            )
            .innerText =
            (
                flight.source?.airportCode ||
                "-"
            )
            +
            " → "
            +
            (
                flight.destination?.airportCode ||
                "-"
            );



        /*
         * STATUS
         */

        const statusElement =
            document.getElementById(
                "flightStatus"
            );


        statusElement.innerText =
            flight.status ||
            "-";


        statusElement.className =
            "status-badge " +
            getStatusClass(
                flight.status
            );



        /*
         * SOURCE
         */

        if (flight.source) {

            document
                .getElementById(
                    "sourceCode"
                )
                .innerText =
                flight.source.airportCode ||
                "-";


            document
                .getElementById(
                    "sourceCity"
                )
                .innerText =
                flight.source.city ||
                "-";


            document
                .getElementById(
                    "sourceAirport"
                )
                .innerText =
                flight.source.airportName ||
                "-";

        }



        /*
         * DESTINATION
         */

        if (flight.destination) {

            document
                .getElementById(
                    "destinationCode"
                )
                .innerText =
                flight.destination.airportCode ||
                "-";


            document
                .getElementById(
                    "destinationCity"
                )
                .innerText =
                flight.destination.city ||
                "-";


            document
                .getElementById(
                    "destinationAirport"
                )
                .innerText =
                flight.destination.airportName ||
                "-";

        }



        /*
         * DEPARTURE
         */

        document
            .getElementById(
                "departureTime"
            )
            .innerText =
            formatDateTime(
                flight.departureDateTime
            );



        /*
         * ARRIVAL
         */

        document
            .getElementById(
                "arrivalTime"
            )
            .innerText =
            formatDateTime(
                flight.arrivalDateTime
            );



        /*
         * AIRCRAFT
         */

        if (flight.aircraft) {

            document
                .getElementById(
                    "aircraftId"
                )
                .innerText =
                flight.aircraft.aircraftId ||
                "-";


            document
                .getElementById(
                    "aircraftModel"
                )
                .innerText =
                flight.aircraft.model ||
                "-";


            document
                .getElementById(
                    "infoAircraftModel"
                )
                .innerText =
                flight.aircraft.model ||
                "-";


            document
                .getElementById(
                    "capacity"
                )
                .innerText =
                flight.aircraft.capacity ||
                "-";

        }



        /*
         * BASE FARE
         */

        document
            .getElementById(
                "baseFare"
            )
            .innerText =
            "₹" +
            Number(
                flight.baseFare || 0
            ).toLocaleString(
                "en-IN"
            );



        /*
         * TOTAL BOOKINGS
         */

        document
            .getElementById(
                "totalBookings"
            )
            .innerText =
            flight.totalBookings ||
            0;



        /*
         * CREATED
         */

        document
            .getElementById(
                "createdAt"
            )
            .innerText =
            formatDateTime(
                flight.createdAt
            );



        /*
         * UPDATED
         */

        document
            .getElementById(
                "updatedAt"
            )
            .innerText =
            formatDateTime(
                flight.updatedAt
            );



        /*
         * CANCEL BUTTON
         */

        const cancelButton =
            document.getElementById(
                "cancelButton"
            );


        if (
            flight.status ===
            "CANCELLED"
            ||
            flight.deleted === true
        ) {

            cancelButton.disabled =
                true;

        } else {

            cancelButton.disabled =
                false;

        }



        /*
         * HIDE LOADING
         */

        document
            .getElementById(
                "loading"
            )
            .classList
            .add("d-none");



        /*
         * SHOW CONTENT
         */

        document
            .getElementById(
                "flightContent"
            )
            .classList
            .remove("d-none");

    }



    /*
     * =====================================================
     * STATUS CLASS
     * =====================================================
     */

    function getStatusClass(
        status
    ) {

        if (
            status === "SCHEDULED"
        ) {

            return "bg-success";

        }


        if (
            status === "DELAYED"
        ) {

            return "bg-warning text-dark";

        }


        if (
            status === "CANCELLED"
        ) {

            return "bg-danger";

        }


        if (
            status === "COMPLETED"
        ) {

            return "bg-primary";

        }


        return "bg-secondary";

    }



    /*
     * =====================================================
     * FORMAT DATE TIME
     * =====================================================
     */

    function formatDateTime(
        value
    ) {

        if (!value) {

            return "-";

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
            "en-IN"
        )
        +
        " "
        +
        date.toLocaleTimeString(
            "en-IN",
            {
                hour: "2-digit",
                minute: "2-digit"
            }
        );

    }



    /*
     * =====================================================
     * VIEW BOOKINGS
     * =====================================================
     */

    function viewBookings() {

        window.location.href =
            CONTEXT_PATH +
            "/admin/flights/" +
            encodeURIComponent(
                FLIGHT_ID
            ) +
            "/bookings";

    }



    /*
     * =====================================================
     * VIEW REVENUE
     * =====================================================
     */

    function viewRevenue() {

        window.location.href =
            CONTEXT_PATH +
            "/admin/flights/" +
            encodeURIComponent(
                FLIGHT_ID
            ) +
            "/revenue";

    }



    /*
     * =====================================================
     * VIEW TICKETS
     * =====================================================
     */

    function viewTickets() {

        window.location.href =
            CONTEXT_PATH +
            "/admin/flights/" +
            encodeURIComponent(
                FLIGHT_ID
            ) +
            "/tickets";

    }



    /*
     * =====================================================
     * CANCEL FLIGHT
     *
     * PATCH
     *
     * /api/v1/admin/flights/{flightId}/cancel
     * =====================================================
     */

    function cancelFlight() {


        if (
            !confirm(
                "Are you sure you want to cancel this flight?"
            )
        ) {

            return;
        }


        const token =
            getToken();


        if (!token) {

            return;
        }


        const apiUrl =
            CONTEXT_PATH +
            "/api/v1/admin/flights/" +
            encodeURIComponent(
                FLIGHT_ID
            ) +
            "/cancel";


        fetch(
            apiUrl,
            {

                method: "PATCH",

                headers: {

                    "Accept":
                        "application/json",

                    "Authorization":
                        "Bearer " + token

                }

            }
        )

        .then(
            function (response) {

                if (
                    response.status === 401 ||
                    response.status === 403
                ) {

                    redirectToLogin();

                    throw new Error(
                        "Session expired. Please login again."
                    );

                }


                if (!response.ok) {

                    return response.text()
                        .then(
                            function (message) {

                                throw new Error(
                                    message ||
                                    "Unable to cancel flight."
                                );

                            }
                        );

                }


                const contentType =
                    response.headers.get(
                        "content-type"
                    );


                if (
                    contentType &&
                    contentType.includes(
                        "application/json"
                    )
                ) {

                    return response.json();

                }


                return response.text();

            }
        )

        .then(
            function (result) {

                let message =
                    "Flight cancelled successfully.";


                if (
                    typeof result ===
                    "string"
                ) {

                    if (result) {

                        message =
                            result;

                    }

                } else if (
                    result &&
                    result.message
                ) {

                    message =
                        result.message;

                }


                showSuccess(
                    message
                );


                /*
                 * Reload flight details
                 */

                setTimeout(
                    function () {

                        loadFlightDetails(
                            FLIGHT_ID
                        );

                    },
                    500
                );

            }
        )

        .catch(
            function (error) {

                console.error(
                    "Cancel Error:",
                    error
                );


                showError(
                    error.message ||
                    "Error cancelling flight."
                );

            }
        );

    }



    /*
     * =====================================================
     * SHOW ERROR
     * =====================================================
     */

    function showError(
        message
    ) {

        document
            .getElementById(
                "loading"
            )
            .classList
            .add("d-none");


        document
            .getElementById(
                "flightContent"
            )
            .classList
            .add("d-none");


        const errorBox =
            document.getElementById(
                "errorMessage"
            );


        document
            .getElementById(
                "errorText"
            )
            .innerText =
            message;


        errorBox
            .classList
            .remove("d-none");

    }



    /*
     * =====================================================
     * SHOW SUCCESS
     * =====================================================
     */

    function showSuccess(
        message
    ) {

        const successBox =
            document.getElementById(
                "successMessage"
            );


        document
            .getElementById(
                "successText"
            )
            .innerText =
            message;


        successBox
            .classList
            .remove("d-none");


        setTimeout(
            function () {

                successBox
                    .classList
                    .add("d-none");

            },
            4000
        );

    }

</script>


</body>

</html>