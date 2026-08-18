<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>My Bookings - Airline Management</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body class="bg-light">
<div class="container py-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="fw-bold">My Bookings</h2>
        <a href="${pageContext.request.contextPath}/flights/search-form" class="btn btn-outline-primary">Book New Flight</a>
    </div>

    <!-- Alert Box for Error Handling -->
    <div id="errorAlert" class="alert alert-danger d-none" role="alert"></div>

    <!-- Loading Spinner -->
    <div id="loader" class="text-center py-5">
        <div class="spinner-border text-primary" role="status">
            <span class="visually-hidden">Loading...</span>
        </div>
        <p class="mt-2 text-muted">Fetching your bookings...</p>
    </div>

    <!-- Bookings Table -->
    <div id="bookingTableContainer" class="card shadow-sm d-none">
        <div class="card-body p-0">
            <div class="table-responsive">
                <table class="table table-hover align-middle mb-0">
                    <thead class="table-dark">
                    <tr>
                        <th>Booking ID</th>
                        <th>Flight Code</th>
                        <th>Route</th>
                        <th>Departure Time</th>
                        <th>Class</th>
                        <th>Amount</th>
                        <th>Status</th>
                        <th class="text-center">Action</th>
                    </tr>
                    </thead>
                    <tbody id="bookingTableBody">
                    <!-- Dynamic content injected here -->
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        fetchBookings();
    });

    function fetchBookings() {
        const token = localStorage.getItem('jwtToken');

        // If no token exists, redirect immediately to login page
        if (!token) {
            window.location.href = "${pageContext.request.contextPath}/users/login";
            return;
        }

        fetch("${pageContext.request.contextPath}/api/v1/user/bookings", {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + token
            }
        })
            .then(response => {
                if (response.status === 401 || response.status === 403) {
                    localStorage.removeItem('jwtToken');
                    window.location.href = "${pageContext.request.contextPath}/users/login";
                    throw new Error("Session expired. Please login again.");
                }
                if (!response.ok) {
                    throw new Error("Failed to load bookings. Server returned status: " + response.status);
                }
                return response.json();
            })
            .then(data => {
                document.getElementById("loader").classList.add("d-none");

                if (data.status === "SUCCESS" && Array.isArray(data.data) && data.data.length > 0) {
                    renderBookings(data.data);
                    document.getElementById("bookingTableContainer").classList.remove("d-none");
                } else {
                    showError("No bookings found.");
                }
            })
            .catch(error => {
                document.getElementById("loader").classList.add("d-none");
                showError(error.message || "Something went wrong while fetching your bookings.");
            });
    }

    function renderBookings(bookings) {
        const tbody = document.getElementById("bookingTableBody");
        tbody.innerHTML = "";

        bookings.forEach(booking => {
            const flight = booking.flightBooked || {};
            const source = flight.source ? flight.source.airportCode : "N/A";
            const destination = flight.destination ? flight.destination.airportCode : "N/A";

            // Parse booking date array or standard string
            const formattedDate = parseBookingDateTime(booking.bookingDateTime);
            const statusBadge = booking.bookingStatus === "CONFIRMED" ? "bg-success" : "bg-warning text-dark";

            const row = `
            <tr>
                <td class="fw-semibold">\${booking.bookingId}</td>
                <td><span class="badge bg-secondary">\${flight.flightCode || 'N/A'}</span></td>
                <td>\${source} &rarr; \${destination}</td>
                <td>\${formatIsoDateTime(flight.departureDateTime) || formattedDate}</td>
                <td>\${formatSeatClass(booking.seatClass)}</td>
                <td class="fw-bold">₹\${booking.amount ? booking.amount.toFixed(2) : '0.00'}</td>
                <td><span class="badge \${statusBadge}">\${booking.bookingStatus}</span></td>
                <td class="text-center">
                    <a href="${pageContext.request.contextPath}/bookings/detail?bookingId=\${booking.bookingId}"
                       class="btn btn-sm btn-primary">
                        View Details
                    </a>
                </td>
            </tr>
        `;
            tbody.insertAdjacentHTML("beforeend", row);
        });
    }

    function parseBookingDateTime(dt) {
        if (Array.isArray(dt) && dt.length >= 3) {
            return `\${dt[0]}-\${String(dt[1]).padStart(2, '0')}-\${String(dt[2]).padStart(2, '0')}`;
        }
        return "N/A";
    }

    function formatIsoDateTime(isoStr) {
        if (!isoStr) return null;
        const date = new Date(isoStr);
        return isNaN(date.getTime()) ? isoStr : date.toLocaleString();
    }

    function formatSeatClass(seatClass) {
        if (!seatClass) return "N/A";
        return seatClass.replace("_", " ").toLowerCase().replace(/\b\w/g, l => l.toUpperCase());
    }

    function showError(msg) {
        const alert = document.getElementById("errorAlert");
        alert.textContent = msg;
        alert.classList.remove("d-none");
    }
</script>
</body>
</html>