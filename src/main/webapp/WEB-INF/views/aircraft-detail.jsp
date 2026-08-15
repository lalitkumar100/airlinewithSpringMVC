<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Aircraft Details</title>
    <style>
        .details-box { width: 50%; margin-top: 20px; border: 1px solid #ddd; padding: 20px; border-radius: 5px; background-color: #f9f9f9; }
        .details-box p { font-size: 16px; line-height: 1.5; }
        .back-btn { display: inline-block; margin-top: 15px; padding: 8px 12px; background-color: #6c757d; color: white; text-decoration: none; border-radius: 4px; }
        .back-btn:hover { background-color: #5a6268; }
    </style>
</head>
<body>

    <h2>Aircraft Details</h2>

    <div class="details-box">
        <p><strong>Aircraft ID:</strong> ${aircraft.aircraftId}</p>
        <p><strong>Model:</strong> ${aircraft.model}</p>
        <p><strong>Capacity:</strong> ${aircraft.capacity}</p>

        <a href="${pageContext.request.contextPath}/aircraft" class="back-btn">Back to List</a>
    </div>

</body>
</html>