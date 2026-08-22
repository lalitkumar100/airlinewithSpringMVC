<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1">

    <title>Flight Revenue</title>

    <style>

        body {
            font-family: Arial, sans-serif;
            background: #f5f6f8;
            margin: 0;
            padding: 30px;
        }

        .container {
            max-width: 800px;
            margin: auto;
        }

        h2 {
            color: #003366;
        }

        .card-container {
            display: flex;
            gap: 20px;
            margin-top: 30px;
        }

        .card {
            background: white;
            padding: 25px;
            border-radius: 10px;
            flex: 1;
            box-shadow: 0 3px 10px rgba(0,0,0,0.1);
        }

        .card h4 {
            margin-top: 0;
            color: #666;
        }

        .amount {
            font-size: 24px;
            font-weight: bold;
        }

        .booking {
            color: #0d6efd;
        }

        .refund {
            color: #dc3545;
        }

        .net {
            color: #198754;
        }

        .message {
            margin-top: 20px;
            padding: 15px;
            border-radius: 5px;
        }

        .error {
            background: #f8d7da;
            color: #842029;
        }

        .loading {
            text-align: center;
            margin-top: 30px;
        }

        .back-btn {
            display: inline-block;
            margin-top: 30px;
            padding: 10px 18px;
            background: #003366;
            color: white;
            text-decoration: none;
            border-radius: 5px;
        }

    </style>

</head>


<body>

<div class="container">

    <h2>Flight Revenue</h2>

    <p>
        Flight ID:
        <strong>${flightId}</strong>
    </p>


    <!-- Loading -->

    <div id="loading" class="loading">
        Loading revenue...
    </div>


    <!-- Error -->

    <div id="error"
         class="message error"
         style="display:none;">
    </div>


    <!-- Revenue -->

    <div id="revenue"
         style="display:none;">

        <div class="card-container">

            <div class="card">

                <h4>Total Booking Amount</h4>

                <div id="bookingAmount"
                     class="amount booking">
                    ₹0.00
                </div>

            </div>


            <div class="card">

                <h4>Total Refund Amount</h4>

                <div id="refundAmount"
                     class="amount refund">
                    ₹0.00
                </div>

            </div>


            <div class="card">

                <h4>Net Revenue</h4>

                <div id="netRevenue"
                     class="amount net">
                    ₹0.00
                </div>

            </div>

        </div>


        <a href="${pageContext.request.contextPath}/admin/flights"
           class="back-btn">

            Back to Flights

        </a>

    </div>

</div>



<script>

    /*
     * Flight ID from Spring MVC
     */

    const flightId =
        "${flightId}";


    /*
     * Context path
     */

    const contextPath =
        "${pageContext.request.contextPath}";


    /*
     * API URL
     */

    const apiUrl =
        contextPath +
        "/api/v1/admin/flights/" +
        encodeURIComponent(flightId) +
        "/revenue";


    /*
     * Format money
     */

    function formatAmount(amount) {

        return "₹" +
            Number(amount || 0).toLocaleString(
                "en-IN",
                {
                    minimumFractionDigits: 2,
                    maximumFractionDigits: 2
                }
            );

    }


    /*
     * Load revenue
     */

    async function loadRevenue() {

        try {

            const token =
                localStorage.getItem("jwtToken");


            /*
             * No token
             */

            if (!token) {

                window.location.href =
                    contextPath +
                    "/users/login";

                return;

            }


            /*
             * Call REST API
             */

            const response =
                await fetch(
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
                );


            /*
             * Unauthorized
             */

            if (
                response.status === 401 ||
                response.status === 403
            ) {

                localStorage.removeItem(
                    "jwtToken"
                );

                window.location.href =
                    contextPath +
                    "/users/login";

                return;

            }


            /*
             * API error
             */

            if (!response.ok) {

                throw new Error(
                    "Unable to load revenue."
                );

            }


            /*
             * JSON response
             */

            const data =
                await response.json();


            console.log(
                "Revenue:",
                data
            );


            /*
             * Display values
             */

            document.getElementById(
                "bookingAmount"
            ).textContent =
                formatAmount(
                    data.totalBookingAmount
                );


            document.getElementById(
                "refundAmount"
            ).textContent =
                formatAmount(
                    data.totalRefundAmount
                );


            document.getElementById(
                "netRevenue"
            ).textContent =
                formatAmount(
                    data.netRevenue
                );


            /*
             * Hide loading
             */

            document.getElementById(
                "loading"
            ).style.display =
                "none";


            /*
             * Show revenue
             */

            document.getElementById(
                "revenue"
            ).style.display =
                "block";

        }


        catch (error) {

            console.error(
                error
            );


            document.getElementById(
                "loading"
            ).style.display =
                "none";


            const errorBox =
                document.getElementById(
                    "error"
                );


            errorBox.textContent =
                error.message;


            errorBox.style.display =
                "block";

        }

    }


    /*
     * Start API call
     */

    loadRevenue();

</script>

</body>

</html>