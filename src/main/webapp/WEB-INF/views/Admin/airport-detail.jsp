<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Airport Details</title>
</head>
<body>
    <h2>Airport Details</h2>
    <p><strong>Airport Code:</strong> ${airport.airportCode}</p>
    <p><strong>Airport Name:</strong> ${airport.airportName}</p>
    <p><strong>City:</strong> ${airport.city}</p>
    <br/>
    <a href="${pageContext.request.contextPath}/airports">Back to List</a>
</body>
</html>