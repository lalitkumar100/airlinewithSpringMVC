### Plan for Show All Bookings Feature

#### 1. Pseudocode

**Controller Layer:**
```java
@GetMapping("/my-bookings")
public String showMyBookings(HttpSession session, Model model) {
    User user = (User) session.getAttribute("loggedUser");
    if (user == null) return "redirect:/login";
    List<Booking> bookings = bookingService.getAllBookingsForUser(user.getId());
    model.addAttribute("bookings", bookings);
    return "show-bookings";
}
```

**Rest Controller Layer:**
```java
@GetMapping("/api/v1/user/bookings")
public ResponseEntity<ApiResponse<List<Booking>>> getMyBookings(HttpServletRequest request) {
    String userId = extractUserIdFromJwt(request);
    List<Booking> bookings = bookingService.getAllBookingsForUser(userId);
    return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "Bookings retrieved", bookings));
}
```

**Service Layer:**
```java
public List<Booking> getAllBookingsForUser(String userId) {
    List<Booking> bookings = bookingMapper.getAllBookingsByUserId(userId);
    for (Booking b : bookings) {
        // Ensure Flight, Source Airport, Destination Airport, and Passengers are populated
        Flight f = flightMapper.getFlightById(b.getFlightId());
        f.setSource(airportMapper.getAirportByCode(f.getSourceCode()));
        f.setDestination(airportMapper.getAirportByCode(f.getDestinationCode()));
        b.setFlightBooked(f);
        b.setPassengers(passengerMapper.getPassengersByBookingId(b.getBookingId()));
    }
    return bookings;
}
```

#### 2. SQL Query
To fetch bookings with flight and airport details:
```sql
SELECT 
    b.booking_id, b.booking_datetime, b.seat_class, b.amount, b.booking_status,
    f.flight_id, f.flight_code, f.departure_datetime, f.arrival_datetime, f.flight_status,
    sa.airport_code AS src_code, sa.airport_name AS src_name, sa.city AS src_city,
    da.airport_code AS dest_code, da.airport_name AS dest_name, da.city AS dest_city
FROM booking b
JOIN flight f ON b.flight_id = f.flight_id
JOIN airport sa ON f.source_airport_code = sa.airport_code
JOIN airport da ON f.destination_airport_code = da.airport_code
WHERE b.user_id = ? AND b.is_deleted = 0
ORDER BY b.booking_datetime DESC;
```

#### 3. REST Request Format
**Endpoint:** `GET /api/v1/user/bookings`
**Headers:** `Authorization: Bearer <JWT_TOKEN>`
**Body:** Empty

#### 4. REST Response Format
```json
{
  "status": "SUCCESS",
  "message": "Bookings retrieved",
  "data": [
    {
      "bookingId": "BK12345",
      "bookingDateTime": "2026-08-18T10:00:00",
      "seatClass": "ECONOMY_CLASS",
      "amount": 5000.0,
      "bookingStatus": "CONFIRMED",
      "flightBooked": {
        "flightCode": "AI-101",
        "source": {
          "airportCode": "DEL",
          "airportName": "Indira Gandhi International",
          "city": "Delhi"
        },
        "destination": {
          "airportCode": "BOM",
          "airportName": "Chhatrapati Shivaji Maharaj",
          "city": "Mumbai"
        },
        "departureDateTime": "2026-08-20T08:00:00",
        "arrivalDateTime": "2026-08-20T10:00:00",
        "status": "SCHEDULED"
      }
    }
  ]
}
```
