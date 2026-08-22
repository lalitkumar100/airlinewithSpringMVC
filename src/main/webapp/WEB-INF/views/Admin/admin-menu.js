<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Admin Dashboard | Air Earth</title>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome Icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

    <style>
        :root {
            --navy: #0b1f3a;
            --navy-light: #12345a;
            --accent-gold: #FFD700;
        }

        body {
            background-color: #f4f7fb;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            min-height: 100vh;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
        }

        .navbar-custom {
            background-color: var(--navy);
            color: white;
            padding: 1rem 0;
            box-shadow: 0 4px 6px rgba(0,0,0,0.1);
        }

        .menu-card {
            border: none;
            border-radius: 16px;
            transition: all 0.3s ease-in-out;
            background: white;
            box-shadow: 0 4px 12px rgba(0,0,0,0.05);
            text-decoration: none;
            overflow: hidden;
        }

        .menu-card:hover {
            transform: translateY(-8px);
            box-shadow: 0 12px 24px rgba(11, 31, 58, 0.15);
        }

        .icon-box {
            width: 70px;
            height: 70px;
            background-color: rgba(11, 31, 58, 0.08);
            color: var(--navy);
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 28px;
            margin: 0 auto 20px;
            transition: all 0.3s ease;
        }

        .menu-card:hover .icon-box {
            background-color: var(--navy);
            color: white;
        }

        .card-title {
            color: var(--navy);
            font-weight: 700;
        }

        .card-text {
            color: #6c757d;
            font-size: 0.9rem;
        }

        footer {
            background-color: var(--navy);
            color: white;
            padding: 1.5rem 0;
            text-align: center;
        }
    </style>
</head>
<body>

    <!-- Navigation Header -->
    <nav class="navbar navbar-custom">
        <div class="container">
            <span class="navbar-brand mb-0 h1 fw-bold text-white">
                <i class="fas fa-user-shield me-2"></i>Air Earth Admin Portal
            </span>
        </div>
    </nav>

    <!-- Main Menu Content -->
    <div class="container py-5 my-auto">
        <div class="text-center mb-5">
            <h1 class="fw-bold" style="color: var(--navy);">Admin Management Dashboard</h1>
            <p class="text-muted">Control and oversee airports, fleets, schedules, and analytics</p>
        </div>

        <div class="row g-4 justify-content-center">

            <!-- Card 1: Airport -->
            <div class="col-md-6 col-lg-4 col-xl-2">
                <a href="${pageContext.request.contextPath}/admin/airports" class="card menu-card h-100 p-4 text-center">
                    <div class="card-body">
                        <div class="icon-box">
                            <i class="fas fa-building"></i>
                        </div>
                        <h4 class="card-title h5 mb-2">Airports</h4>
                        <p class="card-text">Manage airport hubs and locations.</p>
                    </div>
                </a>
            </div>

            <!-- Card 2: Aircraft -->
            <div class="col-md-6 col-lg-4 col-xl-2">
                <a href="${pageContext.request.contextPath}/admin/aircraft" class="card menu-card h-100 p-4 text-center">
                    <div class="card-body">
                        <div class="icon-box">
                            <i class="fas fa-plane"></i>
                        </div>
                        <h4 class="card-title h5 mb-2">Aircraft</h4>
                        <p class="card-text">Oversee fleet details and capacities.</p>
                    </div>
                </a>
            </div>

            <!-- Card 3: Flights -->
            <div class="col-md-6 col-lg-4 col-xl-2">
                <a href="${pageContext.request.contextPath}/admin/flights" class="card menu-card h-100 p-4 text-center">
                    <div class="card-body">
                        <div class="icon-box">
                            <i class="fas fa-route"></i>
                        </div>
                        <h4 class="card-title h5 mb-2">Flights</h4>
                        <p class="card-text">Schedule and monitor active routes.</p>
                    </div>
                </a>
            </div>

            <!-- Card 4: Revenue -->
            <div class="col-md-6 col-lg-4 col-xl-2">
                <a href="${pageContext.request.contextPath}/admin/revenue" class="card menu-card h-100 p-4 text-center">
                    <div class="card-body">
                        <div class="icon-box">
                            <i class="fas fa-chart-line"></i>
                        </div>
                        <h4 class="card-title h5 mb-2">Revenue</h4>
                        <p class="card-text">Analyze financial reports and sales.</p>
                    </div>
                </a>
            </div>

            <!-- Card 5: Profile -->
            <div class="col-md-6 col-lg-4 col-xl-2">
                <a href="${pageContext.request.contextPath}/admin/profile" class="card menu-card h-100 p-4 text-center">
                    <div class="card-body">
                        <div class="icon-box">
                            <i class="fas fa-user-cog"></i>
                        </div>
                        <h4 class="card-title h5 mb-2">Profile</h4>
                        <p class="card-text">Update admin credentials & settings.</p>
                    </div>
                </a>
            </div>

        </div>
    </div>

    <!-- Footer -->
    <footer>
        <div class="container">
            <small>&copy; 2026 Air Earth Management System. Admin Control Panel.</small>
        </div>
    </footer>

    <!-- Bootstrap JS Bundle -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>