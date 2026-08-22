<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Flight Search Results |  Air earth </title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        :root {
            --navy-blue: #003366;
            --navy-dark: #002244;
            --accent-gold: #FFD700;
            --light-gray: #f8f9fa;
        }
        body {
            background-color: var(--light-gray);
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        .navbar-custom {
            background-color: var(--navy-blue);
            color: white;
            padding: 1rem 0;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .search-summary-card {
            background: white;
            border-left: 5px solid var(--navy-blue);
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0,0,0,0.05);
            margin-top: -30px;
            padding: 20px;
        }
        .page-header {
            background-color: var(--navy-blue);
            color: white;
            padding: 60px 0 80px 0;
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
    </style>
</head>
<body class="bg-light">

    <!-- Header Section -->
    <div class="page-header text-center">
        <div class="container">
            <h1 class="display-5 fw-bold"><i class="fas fa-plane-departure me-2"></i>Flight Search Results</h1>
            <p class="lead opacity-75">Fly high with luxury and comfort</p>
        </div>
    </div>

    <div class="container">
        <!-- Search Summary Card -->
        <div class="search-summary-card mb-5">
            <div class="row align-items-center">
                <div class="col-md-8">
                    <h5 class="mb-1 text-muted">Journey Details</h5>
                    <div class="d-flex align-items-center flex-wrap">
                        <span class="fs-4 fw-bold text-navy" id="lblSource"></span>
                        <i class="fas fa-long-arrow-alt-right mx-3 fs-4 text-secondary"></i>
                        <span class="fs-4 fw-bold text-navy" id="lblDestination"></span>
                        <span class="ms-md-4 text-muted fs-6"><i class="far fa-calendar-alt me-2"></i><span id="lblDate"></span></span>
                    </div>
                </div>
                <div class="col-md-4 text-md-end mt-3 mt-md-0">
                    <a href="${pageContext.request.contextPath}/flights/search-form" class="btn btn-outline-navy rounded-pill px-4">
                        <i class="fas fa-edit me-2"></i>Modify Search
                    </a>
                </div>
            </div>
        </div>

        <!-- Alert for no flights -->
        <div id="noFlightsAlert" class="alert alert-info d-none shadow-sm border-0" role="alert">
            <div class="d-flex align-items-center">
                <i class="fas fa-info-circle fs-4 me-3"></i>
                <div>
                    <strong>No flights found.</strong> We couldn't find any flights matching your criteria. Please try different dates or locations.
                </div>
            </div>
        </div>

        <!-- Flight Table Container -->
        <div id="flightTableContainer" class="card shadow-sm border-0 d-none">
            <div class="card-body p-0">
                <div class="table-responsive">
                    <table id="flightTable" class="table table-hover align-middle mb-0">
                        <thead class="bg-navy text-white">
                            <tr>
                                <th class="ps-4">Flight</th>
                                <th>Route</th>
                                <th>Schedule</th>
                                <th>Fare</th>
                                <th>Status</th>
                                <th class="text-center pe-4">Action</th>
                            </tr>
                        </thead>
                        <tbody id="flightTableBody">
                            <!-- Data will be populated dynamically via JavaScript -->
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <!-- JavaScript to fetch data from REST API -->
    <script>
        // Extract query parameters from the URL (e.g., ?source=AMD&destination=COK&departureDate=2026-10-01)
        const urlParams = new URLSearchParams(window.location.search);
        const source = urlParams.get('source');
        const destination = urlParams.get('destination');
        const departureDate = urlParams.get('departureDate');

        // Set labels on the page
        document.getElementById('lblSource').innerText = source || 'N/A';
        document.getElementById('lblDestination').innerText = destination || 'N/A';
        document.getElementById('lblDate').innerText = departureDate || 'N/A';

        if (source && destination && departureDate) {
            // Construct your REST API URL matching your format
            const apiUrl = `${pageContext.request.contextPath}/api/v1/flights/search?source=\${source}&destination=\${destination}&date=\${departureDate}`;

            fetch(apiUrl)
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    return response.json();
                })
                .then(flights => {
                    const tableBody = document.getElementById('flightTableBody');
                    const tableContainer = document.getElementById('flightTableContainer');
                    const noFlightsAlert = document.getElementById('noFlightsAlert');

                    tableBody.innerHTML = '';

                    if (!flights || flights.length === 0) {
                        noFlightsAlert.classList.remove('d-none');
                        tableContainer.classList.add('d-none');
                    } else {
                        noFlightsAlert.classList.add('d-none');
                        tableContainer.classList.remove('d-none');

                        flights.forEach(flight => {
                            const row = document.createElement('tr');
                            row.className = 'flight-row';
                            row.innerHTML = `
                                <td class="ps-4">
                                    <div class="d-flex align-items-center">
                                        <div class="bg-light rounded p-2 me-3">
                                            <i class="fas fa-plane text-navy"></i>
                                        </div>
                                        <div>
                                            <div class="fw-bold text-navy">\${flight.flightCode}</div>
                                            <small class="text-muted">Economy</small>
                                        </div>
                                    </div>
                                </td>
                                <td>
                                    <div class="d-flex flex-column">
                                        <span>\${flight.source.airportCode} <i class="fas fa-arrow-right mx-1 text-muted small"></i> \${flight.destination.airportCode}</span>
                                        <small class="text-muted">\${flight.source.city} to \${flight.destination.city}</small>
                                    </div>
                                </td>
                                <td>
                                    <div class="d-flex flex-column">
                                        <div><i class="far fa-clock me-1 text-muted"></i> \${flight.departureDateTime}</div>
                                        <div class="small text-muted">Arrives: \${flight.arrivalDateTime}</div>
                                    </div>
                                </td>
                                <td>
                                    <span class="price-tag">$\${flight.baseFare}</span>
                                </td>
                                <td>
                                    <span class="badge rounded-pill bg-success-subtle text-success border border-success-subtle badge-status">\${flight.status}</span>
                                </td>
                                <td class="text-center pe-4">
                                    <a href="${pageContext.request.contextPath}/bookings/new?flightId=\${flight.flightId}" class="btn btn-navy rounded-pill px-4 btn-sm">
                                        Book Now
                                    </a>
                                </td>
                            `;
                            tableBody.appendChild(row);
                        });
                    }
                })
                .catch(error => {
                    console.error('Error fetching flight search results:', error);
                    alert('An error occurred while fetching flight data.');
                });
        }
    </script>
</body>
</html>