
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>My Bookings | Air India</title>

    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">

    <style>

        /* ================================
           GLOBAL
        ================================= */

        * {
            box-sizing: border-box;
        }

        body {
            margin: 0;
            font-family: "Segoe UI", Arial, sans-serif;
            background: #f3f7fc;
            color: #172033;
        }

        a {
            text-decoration: none !important;
        }


        /* ================================
           NAVBAR
        ================================= */

        .top-navbar {
            background: linear-gradient(135deg, #031b4e, #063b88);
            padding: 14px 0;
            box-shadow: 0 4px 15px rgba(0, 30, 80, 0.20);
        }

        .brand {
            color: #ffffff !important;
            font-size: 25px;
            font-weight: 700;
            letter-spacing: 0.5px;
        }

        .brand-icon {
            display: inline-flex;
            width: 38px;
            height: 38px;
            align-items: center;
            justify-content: center;
            background: rgba(255,255,255,0.15);
            border-radius: 50%;
            margin-right: 8px;
        }

        .navbar-link {
            color: #ffffff !important;
            margin-left: 22px;
            font-size: 15px;
            font-weight: 500;
            transition: 0.3s;
        }

        .navbar-link:hover {
            color: #8ec5ff !important;
        }

        .welcome-text {
            color: #dceaff;
            margin-left: 20px;
            font-size: 14px;
        }

        .logout-btn {
            border: 1px solid rgba(255,255,255,0.5);
            padding: 7px 15px;
            border-radius: 20px;
            margin-left: 18px;
        }

        .logout-btn:hover {
            background: #ffffff;
            color: #063b88 !important;
        }


        /* ================================
           PAGE HEADER
        ================================= */

        .page-header {
            padding: 38px 0 25px;
        }

        .page-header h1 {
            color: #06245d;
            font-size: 32px;
            font-weight: 700;
            margin-bottom: 5px;
        }

        .page-header p {
            color: #718096;
            margin-bottom: 0;
        }


        /* ================================
           EMPTY BOOKINGS
        ================================= */

        .empty-box {
            background: #ffffff;
            border-radius: 14px;
            padding: 55px 30px;
            text-align: center;
            box-shadow: 0 5px 25px rgba(0, 40, 100, 0.08);
            margin-bottom: 30px;
        }

        .empty-icon {
            font-size: 55px;
            margin-bottom: 15px;
        }

        .empty-box h4 {
            color: #06245d;
            font-weight: 600;
        }

        .empty-box p {
            color: #718096;
        }

        .search-flight-btn {
            background: linear-gradient(135deg, #063b88, #075fc4);
            color: white !important;
            padding: 11px 25px;
            border-radius: 25px;
            display: inline-block;
            margin-top: 10px;
            font-weight: 600;
        }

        .search-flight-btn:hover {
            background: #031b4e;
        }


        /* ================================
           BOOKING CARD
        ================================= */

        .booking-card {
            background: #ffffff;
            border: none;
            border-radius: 16px;
            margin-bottom: 22px;
            overflow: hidden;
            box-shadow: 0 5px 22px rgba(0, 38, 90, 0.09);
            transition: all 0.25s ease;
            position: relative;
        }

        .booking-card:hover {
            transform: translateY(-3px);
            box-shadow: 0 10px 30px rgba(0, 38, 90, 0.14);
        }

        .booking-card::before {
            content: "";
            position: absolute;
            left: 0;
            top: 0;
            bottom: 0;
            width: 5px;
            background: linear-gradient(to bottom, #063b88, #1683ff);
        }

        .booking-body {
            padding: 25px;
        }


        /* ================================
           BOOKING INFORMATION
        ================================= */

        .booking-id {
            color: #06245d;
            font-size: 17px;
            font-weight: 700;
        }

        .booking-date {
            color: #8794a8;
            font-size: 13px;
            margin-top: 6px;
        }


        /* ================================
           STATUS BADGES
        ================================= */

        .status-confirmed {
            display: inline-block;
            background: #e6f7ee;
            color: #16824b;
            padding: 5px 12px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: 700;
            margin-top: 8px;
        }

        .status-other {
            display: inline-block;
            background: #fff3d6;
            color: #9a6700;
            padding: 5px 12px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: 700;
            margin-top: 8px;
        }


        /* ================================
           FLIGHT ROUTE
        ================================= */

        .route-section {
            padding: 0 20px;
        }

        .airport {
            font-size: 19px;
            font-weight: 700;
            color: #06245d;
        }

        .airport-code {
            font-size: 12px;
            color: #8290a5;
            font-weight: 600;
        }

        .flight-time {
            font-size: 13px;
            color: #526174;
            margin-top: 4px;
        }

        .flight-route {
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 0 15px;
        }

        .route-line {
            height: 2px;
            width: 75px;
            background: #a8c5e8;
            position: relative;
        }

        .route-line::after {
            content: "✈";
            position: absolute;
            left: 50%;
            top: -13px;
            transform: translateX(-50%);
            color: #075fc4;
            font-size: 20px;
            background: #ffffff;
            padding: 0 6px;
        }

        .flight-code {
            color: #526174;
            font-size: 11px;
            font-weight: 700;
            letter-spacing: 1px;
            margin-top: 17px;
        }


        /* ================================
           PRICE
        ================================= */

        .price-label {
            color: #8491a5;
            font-size: 12px;
            margin-bottom: 2px;
        }

        .price {
            color: #06245d;
            font-size: 24px;
            font-weight: 700;
        }

        .details-btn {
            margin-top: 9px;
            border: 1px solid #075fc4;
            color: #075fc4;
            background: transparent;
            border-radius: 22px;
            padding: 7px 17px;
            font-size: 13px;
            font-weight: 600;
            transition: 0.25s;
        }

        .details-btn:hover {
            background: #075fc4;
            color: white;
        }


        /* ================================
           FLIGHT STATUS
        ================================= */

        .flight-status {
            margin-top: 15px;
            padding-top: 12px;
            border-top: 1px solid #edf1f6;
            color: #66758a;
            font-size: 13px;
        }

        .scheduled {
            color: #075fc4;
            font-weight: 700;
        }

        .cancelled {
            color: #d93025;
            font-weight: 700;
        }

        .completed {
            color: #16824b;
            font-weight: 700;
        }


        /* ================================
           MODAL
        ================================= */

        .modal-content {
            border: none;
            border-radius: 16px;
            overflow: hidden;
            box-shadow: 0 15px 50px rgba(0, 25, 70, 0.25);
        }

        .modal-header {
            background: linear-gradient(135deg, #031b4e, #063b88);
            color: white;
            border: none;
            padding: 18px 25px;
        }

        .modal-title {
            font-weight: 600;
        }

        .modal-header .close {
            color: white;
            opacity: 0.9;
        }

        .modal-body {
            padding: 25px;
        }

        .section-title {
            color: #06245d;
            font-weight: 700;
            font-size: 17px;
            margin-bottom: 13px;
        }

        .details-table th {
            color: #526174;
            font-weight: 600;
            width: 35%;
            background: #f7faff;
        }

        .details-table td {
            color: #172033;
        }

        .passenger-table thead {
            background: #06245d;
            color: white;
        }

        .passenger-table th {
            font-weight: 600;
        }

        .total-box {
            background: #f0f6ff;
            border-radius: 10px;
            padding: 15px 18px;
            color: #06245d;
        }


        /* ================================
           FOOTER
        ================================= */

        .footer {
            text-align: center;
            color: #8a96a8;
            font-size: 13px;
            padding: 30px 0;
        }


        /* ================================
           MOBILE
        ================================= */

        @media (max-width: 767px) {

            .navbar-link {
                margin-left: 0;
                margin-top: 10px;
            }

            .welcome-text {
                margin-left: 0;
                display: block;
                margin-top: 10px;
            }

            .logout-btn {
                margin-left: 0;
                margin-top: 10px;
                display: inline-block;
            }

            .page-header h1 {
                font-size: 27px;
            }

            .route-section {
                margin-top: 20px;
                margin-bottom: 20px;
                padding: 0;
            }

            .price-section {
                text-align: left !important;
                margin-top: 15px;
            }

            .route-line {
                width: 45px;
            }
        }

    </style>
</head>

<body>


<!-- ================================
     NAVIGATION BAR
================================= -->

<nav class="navbar navbar-expand-lg top-navbar">

    <div class="container">

        <a class="navbar-brand brand"
           href="${pageContext.request.contextPath}/bookings/my-bookings">

            <span class="brand-icon">✈</span>
            Air India
        </a>

        <div class="ml-auto d-flex align-items-center flex-wrap">

            <span class="welcome-text">
                Welcome, ${loggedUser.firstName}
            </span>

            <a class="navbar-link"
               href="${pageContext.request.contextPath}/flights/search-form">
                Search Flights
            </a>

            <a class="navbar-link logout-btn"
               href="#"
               onclick="handleLogout(); return false;">
                Logout
            </a>

        </div>

    </div>

</nav>


<!-- ================================
     MAIN CONTENT
================================= -->

<div class="container">

    <!-- Page Header -->

    <div class="page-header">

        <h1>My Bookings</h1>

        <p>
            View and manage your upcoming and previous flight bookings.
        </p>

    </div>


    <!-- ================================
         EMPTY BOOKINGS
    ================================= -->

    <c:if test="${empty bookings}">

        <div class="empty-box">

            <div class="empty-icon">
                ✈️
            </div>

            <h4>No Bookings Found</h4>

            <p>
                You have not booked any flights yet.
            </p>

            <a class="search-flight-btn"
               href="${pageContext.request.contextPath}/flights/search-form">

                Search Flights

            </a>

        </div>

    </c:if>


    <!-- ================================
         BOOKING LIST
    ================================= -->

    <div class="row">

        <c:forEach var="booking" items="${bookings}">

            <div class="col-12">

                <div class="booking-card">

                    <div class="booking-body">

                        <div class="row align-items-center">


                            <!-- ================================
                                 BOOKING INFORMATION
                            ================================= -->

                            <div class="col-lg-3 col-md-4">

                                <div class="booking-id">
                                    Booking #${booking.bookingId}
                                </div>

                                <div class="booking-date">

                                    <fmt:parseDate
                                            value="${booking.bookingDateTime}"
                                            pattern="yyyy-MM-dd'T'HH:mm"
                                            var="parsedDate"
                                            type="both"/>

                                    Booked on:
                                    <fmt:formatDate
                                            value="${parsedDate}"
                                            pattern="dd MMM yyyy, HH:mm"/>

                                </div>


                                <c:choose>

                                    <c:when test="${booking.bookingStatus == 'CONFIRMED'}">

                                        <span class="status-confirmed">
                                            ✓ ${booking.bookingStatus}
                                        </span>

                                    </c:when>

                                    <c:otherwise>

                                        <span class="status-other">
                                                ${booking.bookingStatus}
                                        </span>

                                    </c:otherwise>

                                </c:choose>

                            </div>


                            <!-- ================================
                                 FLIGHT ROUTE
                            ================================= -->

                            <div class="col-lg-6 col-md-5 route-section">

                                <div class="d-flex align-items-center justify-content-between">


                                    <!-- SOURCE -->

                                    <div>

                                        <div class="airport">
                                                ${booking.flightBooked.source.city}
                                        </div>

                                        <div class="airport-code">
                                                ${booking.flightBooked.source.airportCode}
                                        </div>

                                        <div class="flight-time">

                                            <fmt:parseDate
                                                    value="${booking.flightBooked.departureDateTime}"
                                                    pattern="yyyy-MM-dd'T'HH:mm"
                                                    var="depDate"
                                                    type="both"/>

                                            <fmt:formatDate
                                                    value="${depDate}"
                                                    pattern="HH:mm"/>

                                        </div>

                                    </div>


                                    <!-- FLIGHT -->

                                    <div class="flight-route">

                                        <div>

                                            <div class="route-line"></div>

                                            <div class="text-center flight-code">
                                                    ${booking.flightBooked.flightCode}
                                            </div>

                                        </div>

                                    </div>


                                    <!-- DESTINATION -->

                                    <div class="text-right">

                                        <div class="airport">
                                                ${booking.flightBooked.destination.city}
                                        </div>

                                        <div class="airport-code">
                                                ${booking.flightBooked.destination.airportCode}
                                        </div>

                                        <div class="flight-time">

                                            <fmt:parseDate
                                                    value="${booking.flightBooked.arrivalDateTime}"
                                                    pattern="yyyy-MM-dd'T'HH:mm"
                                                    var="arrDate"
                                                    type="both"/>

                                            <fmt:formatDate
                                                    value="${arrDate}"
                                                    pattern="HH:mm"/>

                                        </div>

                                    </div>

                                </div>


                                <!-- FLIGHT STATUS -->

                                <div class="flight-status">

                                    Flight Status:

                                    <c:choose>

                                        <c:when test="${booking.flightBooked.status == 'SCHEDULED'}">

                                            <span class="scheduled">
                                                    ${booking.flightBooked.status}
                                            </span>

                                        </c:when>

                                        <c:when test="${booking.flightBooked.status == 'COMPLETED'}">

                                            <span class="completed">
                                                    ${booking.flightBooked.status}
                                            </span>

                                        </c:when>

                                        <c:otherwise>

                                            <span class="cancelled">
                                                    ${booking.flightBooked.status}
                                            </span>

                                        </c:otherwise>

                                    </c:choose>

                                </div>

                            </div>


                            <!-- ================================
                                 PRICE
                            ================================= -->

                            <div class="col-lg-3 col-md-3 price-section text-right">

                                <div class="price-label">
                                    TOTAL PAID
                                </div>

                                <div class="price">
                                    ₹${booking.amount}
                                </div>

                                <button
                                        type="button"
                                        class="details-btn"
                                        onclick="window.location.href='${pageContext.request.contextPath}/bookings/detail?bookingId=${booking.bookingId}'">

                                    View Details

                                </button>

                            </div>

                        </div>

                    </div>

                </div>


                <!-- ================================
                     BOOKING DETAILS MODAL
                ================================= -->

                <div class="modal fade"
                     id="details-${booking.bookingId}"
                     tabindex="-1"
                     role="dialog">

                    <div class="modal-dialog modal-lg modal-dialog-centered"
                         role="document">

                        <div class="modal-content">


                            <!-- MODAL HEADER -->

                            <div class="modal-header">

                                <h5 class="modal-title">

                                    Booking Details
                                    <span style="opacity:0.75;">
                                        #${booking.bookingId}
                                    </span>

                                </h5>

                                <button type="button"
                                        class="close"
                                        data-dismiss="modal">

                                    <span>&times;</span>

                                </button>

                            </div>


                            <!-- MODAL BODY -->

                            <div class="modal-body">


                                <!-- FLIGHT INFORMATION -->

                                <div class="section-title">
                                    ✈ Flight Information
                                </div>

                                <table class="table table-bordered details-table">

                                    <tr>
                                        <th>Flight Code</th>
                                        <td>
                                                ${booking.flightBooked.flightCode}
                                        </td>
                                    </tr>

                                    <tr>
                                        <th>Source</th>
                                        <td>
                                                ${booking.flightBooked.source.airportName}
                                            (${booking.flightBooked.source.city})
                                        </td>
                                    </tr>

                                    <tr>
                                        <th>Destination</th>
                                        <td>
                                                ${booking.flightBooked.destination.airportName}
                                            (${booking.flightBooked.destination.city})
                                        </td>
                                    </tr>

                                    <tr>
                                        <th>Departure</th>
                                        <td>
                                                ${booking.flightBooked.departureDateTime}
                                        </td>
                                    </tr>

                                    <tr>
                                        <th>Arrival</th>
                                        <td>
                                                ${booking.flightBooked.arrivalDateTime}
                                        </td>
                                    </tr>

                                    <tr>
                                        <th>Flight Status</th>
                                        <td>
                                                ${booking.flightBooked.status}
                                        </td>
                                    </tr>

                                </table>


                                <!-- PASSENGER INFORMATION -->

                                <div class="section-title mt-4">
                                    👤 Passenger Details
                                </div>

                                <div class="table-responsive">

                                    <table class="table table-hover passenger-table">

                                        <thead>

                                        <tr>
                                            <th>Name</th>
                                            <th>Gender</th>
                                            <th>Email</th>
                                        </tr>

                                        </thead>

                                        <tbody>

                                        <c:forEach
                                                var="p"
                                                items="${booking.passengers}">

                                            <tr>

                                                <td>
                                                        ${p.firstName} ${p.lastName}
                                                </td>

                                                <td>
                                                        ${p.gender}
                                                </td>

                                                <td>
                                                        ${p.email}
                                                </td>

                                            </tr>

                                        </c:forEach>

                                        </tbody>

                                    </table>

                                </div>


                                <!-- PAYMENT INFORMATION -->

                                <div class="total-box mt-4">

                                    <div class="row">

                                        <div class="col-md-6">

                                            <strong>Seat Class:</strong>
                                                ${booking.seatClass}

                                        </div>

                                        <div class="col-md-6 text-md-right">

                                            <strong>Total Paid:</strong>
                                            ₹${booking.amount}

                                        </div>

                                    </div>

                                </div>

                            </div>


                            <!-- MODAL FOOTER -->

                            <div class="modal-footer">

                                <button
                                        type="button"
                                        class="btn btn-secondary"
                                        data-dismiss="modal">

                                    Close

                                </button>

                            </div>

                        </div>

                    </div>

                </div>

            </div>

        </c:forEach>

    </div>

</div>


<!-- ================================
     FOOTER
================================= -->

<div class="footer">

    © 2026 Air India · Airline Management System

</div>


<!-- ================================
     JAVASCRIPT
================================= -->

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>

<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>

<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>


<script>

    /*
     * JWT CHECK
     *
     * This checks whether a JWT token exists
     * before allowing the user to remain on the page.
     *
     * IMPORTANT:
     * The backend should ALSO validate the JWT.
     * This JavaScript check alone is not security.
     */

    (function () {

        const token = localStorage.getItem("jwtToken");

        if (!token) {

            window.location.href =
                '${pageContext.request.contextPath}/users/login';

        }

    })();


    /*
     * LOGOUT
     */

    function handleLogout() {

        // Remove JWT from browser storage
        localStorage.removeItem("jwtToken");

        // Redirect to server-side logout
        window.location.href =
            '${pageContext.request.contextPath}/logout';

    }

</script>

</body>

</html>

