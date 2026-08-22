<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Search Flights</title>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">

    <style>

        :root {
            --navy: #0b1f3a;
            --navy-light: #12345a;
        }

        body {
            background: #f4f7fb;
        }

        .search-card {
            border: none;
            border-radius: 18px;
            overflow: hidden;
        }

        .search-header {
            background: var(--navy);
            color: white;
        }

        .brand-icon {
            font-size: 32px;
        }

        .form-control:focus,
        .form-select:focus {
            border-color: var(--navy-light);
            box-shadow: 0 0 0 0.2rem rgba(11, 31, 58, 0.15);
        }

        .btn-navy {
            background-color: var(--navy);
            color: white;
            border: none;
        }

        .btn-navy:hover {
            background-color: var(--navy-light);
            color: white;
        }

        .btn-outline-navy {
            border: 1px solid var(--navy);
            color: var(--navy);
        }

        .btn-outline-navy:hover {
            background-color: var(--navy);
            color: white;
        }

        .airport-help {
            font-size: 12px;
            color: #6c757d;
        }

    </style>

</head>

<body>

<div class="container py-4 py-md-5">

    <div class="row justify-content-center">

        <div class="col-12 col-lg-9">

            <div class="card search-card shadow-lg">

                <!-- Header -->
                <div class="search-header text-center p-4">

                    <div class="brand-icon mb-2">
                        ✈
                    </div>

                    <h2 class="fw-bold mb-1">
                        Search Available Flights
                    </h2>

                    <p class="mb-0 opacity-75">
                        Find the best flight for your journey
                    </p>

                </div>


                <!-- Search Form -->
                <div class="card-body p-4 p-md-5">

                    <form action="${pageContext.request.contextPath}/flights/search"
                          method="get"
                          class="row g-4">


                        <!-- Source -->
                        <div class="col-md-4">

                            <label for="source"
                                   class="form-label fw-semibold">
                                Source Airport
                            </label>

                            <select name="source"
                                    id="source"
                                    class="form-select">

                                <option value="" selected>
                                    -- Select Source --
                                </option>

                                <c:forEach var="airport" items="${airports}">

                                    <option value="${airport.airportCode}">
                                            ${airport.airportName}
                                        (${airport.airportCode}) -
                                            ${airport.city}
                                    </option>

                                </c:forEach>

                            </select>

                            <div class="airport-help mt-1">
                                Select your departure airport
                            </div>

                        </div>


                        <!-- Destination -->
                        <div class="col-md-4">

                            <label for="destination"
                                   class="form-label fw-semibold">
                                Destination Airport
                            </label>

                            <select name="destination"
                                    id="destination"
                                    class="form-select">

                                <option value="" selected>
                                    -- Select Destination --
                                </option>

                                <c:forEach var="airport" items="${airports}">

                                    <option value="${airport.airportCode}">
                                            ${airport.airportName}
                                        (${airport.airportCode}) -
                                            ${airport.city}
                                    </option>

                                </c:forEach>

                            </select>

                            <div class="airport-help mt-1">
                                Select your arrival airport
                            </div>

                        </div>


                        <!-- Departure Date -->
                        <div class="col-md-4">

                            <label for="departureDate"
                                   class="form-label fw-semibold">
                                Departure Date
                            </label>

                            <input type="date"
                                   name="departureDate"
                                   id="departureDate"
                                   class="form-control">

                            <div class="airport-help mt-1">
                                Select your travel date
                            </div>

                        </div>


                        <!-- Buttons -->
                        <div class="col-12 mt-3">

                            <div class="d-flex flex-column flex-sm-row gap-2">

                                <button type="submit"
                                        class="btn btn-navy px-4">

                                    🔍 Search Flights

                                </button>

                                <a href="${pageContext.request.contextPath}/flights/search"
                                   class="btn btn-outline-navy px-4">

                                    View All Flights

                                </a>

                            </div>

                        </div>

                    </form>

                </div>

            </div>


            <!-- Footer -->
            <div class="text-center mt-3">

                <small class="text-muted">
                    Airline Management System
                </small>

            </div>

        </div>

    </div>

</div>


<script>

    // Prevent selecting the same airport
    document.getElementById('source')
        .addEventListener('change', function () {

            const destination =
                document.getElementById('destination');

            if (this.value && this.value === destination.value) {
                destination.value = "";
            }

        });

    document.getElementById('destination')
        .addEventListener('change', function () {

            const source =
                document.getElementById('source');

            if (this.value && this.value === source.value) {
                source.value = "";
            }

        });


    // Prevent selecting a past date
    const today =
        new Date().toISOString().split('T')[0];

    document.getElementById('departureDate')
        .setAttribute('min', today);

</script>

</body>
</html>