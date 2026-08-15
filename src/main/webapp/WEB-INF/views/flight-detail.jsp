<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Flight Management</title>
</head>
<body>
    <h2>Flight Details: ${flight.flightCode}</h2>
    <p><strong>Flight ID:</strong> ${flight.flightId}</p>
    <p><strong>Source:</strong> ${flight.source.airportName} (${flight.source.airportCode})</p>
    <p><strong>Destination:</strong> ${flight.destination.airportName} (${flight.destination.airportCode})</p>
    <p><strong>Base Fare:</strong> ${flight.baseFare}</p>
    <p><strong>Current Status:</strong> ${flight.status}</p>

    <hr/>

    <h3>Update Flight Schedule</h3>
    <form action="${pageContext.request.contextPath}/flights/update-time/${flight.flightId}" method="post">
        <label>Departure Time:</label>
        <input type="datetime-local" name="departureDateTime" required/><br/><br/>
        <label>Arrival Time:</label>
        <input type="datetime-local" name="arrivalDateTime" required/><br/><br/>
        <button type="submit">Update Schedule</button>
    </form>

    <hr/>

    <h3>Update Flight Status</h3>
    <form action="${pageContext.request.contextPath}/flights/update-status/${flight.flightId}" method="post">
        <label>Status:</label>
        <select name="status">
            <c:forEach var="st" items="${statuses}">
                <option value="${st}" <c:if test="${flight.status == st}">selected</c:if>>${st}</option>
            </c:forEach>
        </select>
        <button type="submit">Update Status</button>
    </form>

    <br/>
    <a href="${pageContext.request.contextPath}/flights">Back to Flight List</a>
</body>
</html>