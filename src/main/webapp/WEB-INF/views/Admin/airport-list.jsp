<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>ABC Airline - Airport & Aircraft</title>


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
           PAGE TITLE
           ===================================================== */

        .page-title {
            color: #071b3a;
            font-weight: bold;
            margin-bottom: 5px;
        }


        .page-subtitle {
            color: #6c757d;
            margin-bottom: 30px;
        }


        /* =====================================================
           SECTION CARD
           ===================================================== */

        .section-card {
            background-color: white;

            border-radius: 8px;

            padding: 25px;

            margin-bottom: 30px;

            box-shadow:
                    0 5px 20px
                    rgba(0, 0, 0, 0.08);
        }


        /* =====================================================
           SECTION HEADER
           ===================================================== */

        .section-header {
            display: flex;

            justify-content: space-between;

            align-items: center;

            margin-bottom: 20px;
        }


        .section-title {
            color: #071b3a;

            font-weight: bold;

            margin: 0;
        }


        /* =====================================================
           TABLE
           ===================================================== */

        .table thead {
            background-color: #071b3a;

            color: white;
        }


        .table thead th {
            background-color: #071b3a;

            color: white;

            border: none;

            padding: 12px;
        }


        .table tbody td {
            padding: 12px;

            vertical-align: middle;
        }


        .table tbody tr:hover {
            background-color: #f4f7fb;
        }


        /* =====================================================
           REFRESH BUTTON
           ===================================================== */

        .btn-refresh {
            background-color: #071b3a;

            color: white;

            border: none;

            padding: 7px 15px;

            border-radius: 5px;

            font-size: 14px;
        }


        .btn-refresh:hover {
            background-color: #0d2c5c;

            color: white;
        }


        /* =====================================================
           BACK BUTTON
           ===================================================== */

        .btn-back {
            background-color: white;

            color: #071b3a;

            border: 1px solid #071b3a;

            padding: 7px 15px;

            border-radius: 5px;

            text-decoration: none;

            font-size: 14px;
        }


        .btn-back:hover {
            background-color: #071b3a;

            color: white;
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


        /* =====================================================
           API MESSAGE
           ===================================================== */

        .api-message {
            margin-bottom: 20px;
        }


        /* =====================================================
           LOADING
           ===================================================== */

        .loading {
            text-align: center;

            color: #6c757d;

            padding: 25px;
        }


        /* =====================================================
           EMPTY
           ===================================================== */

        .empty-message {
            text-align: center;

            color: #6c757d;

            padding: 25px;
        }

    </style>

</head>


<body>


<!-- =========================================================
     NAVBAR
     ========================================================= -->

<nav class="navbar navbar-dark">

    <div class="container">


        <!-- BRAND -->

        <a
                class="navbar-brand"
                href="${pageContext.request.contextPath}/admin/menu">

            ✈ ABC Airline

        </a>


        <!-- RIGHT SIDE -->

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

    <div class="d-flex justify-content-between align-items-center mb-4">

        <div>

            <h2 class="page-title">

                Airport & Aircraft

            </h2>

            <p class="page-subtitle mb-0">

                View and manage airline airports and aircraft.

            </p>

        </div>


        <a
                href="${pageContext.request.contextPath}/admin/menu"
                class="btn-back">

            ← Back

        </a>

    </div>



    <!-- =====================================================
         API MESSAGE
         ===================================================== -->

    <div
            id="apiMessage"
            class="api-message">

    </div>



    <!-- =====================================================
         AIRCRAFT SECTION
         ===================================================== -->

    <div class="section-card">


        <!-- HEADER -->

        <div class="section-header">

            <h4 class="section-title">

                ✈ Aircraft

            </h4>


            <button
                    type="button"
                    id="refreshAircraft"
                    class="btn-refresh">

                Refresh

            </button>

        </div>



        <!-- TABLE -->

        <div class="table-responsive">

            <table
                    class="table table-bordered table-hover mb-0">


                <thead>

                <tr>

                    <th>
                        #
                    </th>

                    <th>
                        Aircraft ID
                    </th>

                    <th>
                        Model
                    </th>

                    <th>
                        Capacity
                    </th>

                </tr>

                </thead>


                <tbody id="aircraftTableBody">

                <tr>

                    <td
                            colspan="4"
                            class="loading">

                        Loading aircraft...

                    </td>

                </tr>

                </tbody>


            </table>

        </div>


    </div>



    <!-- =====================================================
         AIRPORT SECTION
         ===================================================== -->

    <div class="section-card">


        <!-- HEADER -->

        <div class="section-header">

            <h4 class="section-title">

                🏢 Airports

            </h4>


            <button
                    type="button"
                    id="refreshAirports"
                    class="btn-refresh">

                Refresh

            </button>

        </div>



        <!-- TABLE -->

        <div class="table-responsive">

            <table
                    class="table table-bordered table-hover mb-0">


                <thead>

                <tr>

                    <th>
                        #
                    </th>

                    <th>
                        Airport Code
                    </th>

                    <th>
                        Airport Name
                    </th>

                    <th>
                        City
                    </th>

                </tr>

                </thead>


                <tbody id="airportTableBody">

                <tr>

                    <td
                            colspan="4"
                            class="loading">

                        Loading airports...

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
       API URLS
       ========================================================= */

    const aircraftApi =
        contextPath + "/api/v1/aircraft/";


    const airportApi =
        contextPath + "/api/v1/airports/";



    /* =========================================================
       ELEMENTS
       ========================================================= */

    const aircraftTableBody =
        document.getElementById(
            "aircraftTableBody"
        );


    const airportTableBody =
        document.getElementById(
            "airportTableBody"
        );


    const apiMessage =
        document.getElementById(
            "apiMessage"
        );



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
       SHOW API ERROR
       ========================================================= */

    function showApiError(message) {

        apiMessage.innerHTML = `

            <div class="alert alert-danger">

                ${message}

            </div>

        `;

    }



    /* =========================================================
       LOAD AIRCRAFT
       ========================================================= */

    async function loadAircraft() {


        /* -----------------------------------------------------
           CHECK TOKEN
           ----------------------------------------------------- */

        if (!checkToken()) {
            return;
        }


        /* -----------------------------------------------------
           LOADING
           ----------------------------------------------------- */

        aircraftTableBody.innerHTML = `

            <tr>

                <td
                        colspan="4"
                        class="loading">

                    Loading aircraft...

                </td>

            </tr>

        `;


        try {


            /* -------------------------------------------------
               API CALL
               ------------------------------------------------- */

            const response =
                await fetch(
                    aircraftApi,
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


                const aircraftList =
                    result.responseData;


                renderAircraft(
                    aircraftList
                );


                return;

            }



            /* -------------------------------------------------
               ERROR

               Display ONLY backend message.
               ------------------------------------------------- */

            const errorMessage =
                result &&
                result.message
                    ? result.message
                    : "Unable to retrieve aircraft.";


            showApiError(
                errorMessage
            );


            aircraftTableBody.innerHTML = `

                <tr>

                    <td
                            colspan="4"
                            class="empty-message">

                        Unable to load aircraft.

                    </td>

                </tr>

            `;


        } catch (error) {


            console.error(error);


            showApiError(
                "Unable to connect to the server."
            );


            aircraftTableBody.innerHTML = `

                <tr>

                    <td
                            colspan="4"
                            class="empty-message">

                        Unable to load aircraft.

                    </td>

                </tr>

            `;

        }

    }



    /* =========================================================
       RENDER AIRCRAFT
       ========================================================= */

    function renderAircraft(
        aircraftList
    ) {


        /* -----------------------------------------------------
           EMPTY
           ----------------------------------------------------- */

        if (
            !aircraftList ||
            aircraftList.length === 0
        ) {

            aircraftTableBody.innerHTML = `

                <tr>

                    <td
                            colspan="4"
                            class="empty-message">

                        No aircraft found.

                    </td>

                </tr>

            `;

            return;

        }



        /* -----------------------------------------------------
           BUILD TABLE
           ----------------------------------------------------- */

        let html = "";


        aircraftList.forEach(
            function (
                aircraft,
                index
            ) {


                html += `

                    <tr>

                        <td>
                            ${index + 1}
                        </td>

                        <td>
                            ${aircraft.aircraftId}
                        </td>

                        <td>
                            ${aircraft.model}
                        </td>

                        <td>
                            ${aircraft.capacity}
                        </td>

                    </tr>

                `;

            }
        );


        aircraftTableBody.innerHTML =
            html;

    }



    /* =========================================================
       LOAD AIRPORTS
       ========================================================= */

    async function loadAirports() {


        /* -----------------------------------------------------
           CHECK TOKEN
           ----------------------------------------------------- */

        if (!checkToken()) {
            return;
        }


        /* -----------------------------------------------------
           LOADING
           ----------------------------------------------------- */

        airportTableBody.innerHTML = `

            <tr>

                <td
                        colspan="4"
                        class="loading">

                    Loading airports...

                </td>

            </tr>

        `;


        try {


            /* -------------------------------------------------
               API CALL
               ------------------------------------------------- */

            const response =
                await fetch(
                    airportApi,
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


                const airportList =
                    result.responseData;


                renderAirports(
                    airportList
                );


                return;

            }



            /* -------------------------------------------------
               ERROR
               -------------------------------------------------

               Display ONLY backend message.
               ------------------------------------------------- */

            const errorMessage =
                result &&
                result.message
                    ? result.message
                    : "Unable to retrieve airports.";


            showApiError(
                errorMessage
            );


            airportTableBody.innerHTML = `

                <tr>

                    <td
                            colspan="4"
                            class="empty-message">

                        Unable to load airports.

                    </td>

                </tr>

            `;


        } catch (error) {


            console.error(error);


            showApiError(
                "Unable to connect to the server."
            );


            airportTableBody.innerHTML = `

                <tr>

                    <td
                            colspan="4"
                            class="empty-message">

                        Unable to load airports.

                    </td>

                </tr>

            `;

        }

    }



    /* =========================================================
       RENDER AIRPORTS
       ========================================================= */

    function renderAirports(
        airportList
    ) {


        /* -----------------------------------------------------
           EMPTY
           ----------------------------------------------------- */

        if (
            !airportList ||
            airportList.length === 0
        ) {

            airportTableBody.innerHTML = `

                <tr>

                    <td
                            colspan="4"
                            class="empty-message">

                        No airports found.

                    </td>

                </tr>

            `;

            return;

        }



        /* -----------------------------------------------------
           BUILD TABLE
           ----------------------------------------------------- */

        let html = "";


        airportList.forEach(
            function (
                airport,
                index
            ) {


                html += `

                    <tr>

                        <td>
                            ${index + 1}
                        </td>

                        <td>
                            ${airport.airportCode}
                        </td>

                        <td>
                            ${airport.airportName}
                        </td>

                        <td>
                            ${airport.city}
                        </td>

                    </tr>

                `;

            }
        );


        airportTableBody.innerHTML =
            html;

    }



    /* =========================================================
       REFRESH AIRCRAFT
       ========================================================= */

    document
        .getElementById(
            "refreshAircraft"
        )
        .addEventListener(
            "click",
            function () {

                apiMessage.innerHTML = "";

                loadAircraft();

            }
        );



    /* =========================================================
       REFRESH AIRPORTS
       ========================================================= */

    document
        .getElementById(
            "refreshAirports"
        )
        .addEventListener(
            "click",
            function () {

                apiMessage.innerHTML = "";

                loadAirports();

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


                /* Remove JWT */

                localStorage.removeItem(
                    "token"
                );


                /* Remove role */

                localStorage.removeItem(
                    "role"
                );


                /* Redirect */

                window.location.href =
                    contextPath + "/login";

            }
        );



    /* =========================================================
       LOAD BOTH TABLES WHEN PAGE OPENS
       ========================================================= */

    document.addEventListener(
        "DOMContentLoaded",
        function () {

            loadAircraft();

            loadAirports();

        }
    );


</script>


</body>

</html>