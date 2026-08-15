<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Flight List</title>
</head>
<body>
    <h2>Available Flights</h2>
    <table border="1">
        <thead>
            <tr>
                <th>Flight ID</th>
                <th>Flight Code</th>
                <th>Source</th>
                <th>Destination</th>
                <th>Departure</th>
                <th>Arrival</th>
                <th>Status</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="flight" items="${flights}">
                <tr>
                    <td>${flight.flightId}</td>
                    <td>${flight.flightCode}</td>
                    <td>${flight.source.airportCode}</td>
                    <td>${flight.destination.airportCode}</td>
                    <td>${flight.departureDateTime}</td>
                    <td>${flight.arrivalDateTime}</td>
                    <td>${flight.status}</td>
                    <td><a href="${pageContext.request.contextPath}/flights/${flight.flightId}">Manage</a></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>