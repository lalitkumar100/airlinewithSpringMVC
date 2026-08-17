<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Search Flights</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="container mt-5">
    <h2>Search Available Flights</h2>
    <hr>
    
    <form action="${pageContext.request.contextPath}/flights/search" method="get" class="row g-3">
        <div class="col-md-4">
            <label for="source" class="form-label">Source Airport:</label>
            <select name="source" id="source" class="form-select" required>
                <option value="" disabled selected>Select Source Airport</option>
                <c:forEach var="airport" items="${airports}">
                    <option value="${airport.airportCode}">${airport.airportName} (${airport.airportCode})</option>
                </c:forEach>
            </select>
        </div>

        <div class="col-md-4">
            <label for="destination" class="form-label">Destination Airport:</label>
            <select name="destination" id="destination" class="form-select" required>
                <option value="" disabled selected>Select Destination Airport</option>
                <c:forEach var="airport" items="${airports}">
                    <option value="${airport.airportCode}">${airport.airportName} (${airport.airportCode})</option>
                </c:forEach>
            </select>
        </div>

        <div class="col-md-4">
            <label for="departureDate" class="form-label">Departure Date:</label>
            <input type="date" name="departureDate" id="departureDate" class="form-control" required>
        </div>

        <div class="col-12 mt-4">
            <button type="submit" class="btn btn-primary">Search Flights</button>
            <a href="${pageContext.request.contextPath}/flights" class="btn btn-secondary">Back to All Flights</a>
        </div>
    </form>
</body>
</html>