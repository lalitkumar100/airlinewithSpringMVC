package com.crimsonlogic.arilinemanangmentsystem.dao;

import com.crimsonlogic.arilinemanangmentsystem.model.Ticket;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TicketMapper {

    @Insert("""
        INSERT INTO ticket (
            ticket_id,
            booking_id,
            passenger_id,
            fare,
            seat_class,
            seat_number,
            created_at,
            updated_at,
            is_deleted
        ) VALUES (
            #{ticketId},
            #{booking.bookingId},
            #{passenger.passengerId},
            #{fare},
            #{seatClass, typeHandler=org.apache.ibatis.type.EnumTypeHandler},
            #{seatNumber},
            NOW(),
            NOW(),
            0
        )
    """)
    int insertTicket(Ticket ticket);

    @Select("""
        SELECT 
            ticket_id,
            booking_id,
            passenger_id,
            fare,
            seat_class,
            seat_number,
            created_at,
            updated_at,
            is_deleted
        FROM ticket
        WHERE booking_id IN (SELECT booking_id FROM booking WHERE flight_id = #{flightId})
          AND is_deleted = 0
    """)
    @Results(id = "TicketResultMap", value = {
            @Result(property = "ticketId", column = "ticket_id", id = true),
            @Result(property = "fare", column = "fare"),
            @Result(property = "seatClass", column = "seat_class", typeHandler = org.apache.ibatis.type.EnumTypeHandler.class),
            @Result(property = "seatNumber", column = "seat_number"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "updatedAt", column = "updated_at"),
            @Result(property = "deleted", column = "is_deleted"),
            @Result(property = "booking.bookingId", column = "booking_id"),
            @Result(property = "passenger.passengerId", column = "passenger_id")
    })
    List<Ticket> getTicketsByFlightId(String flightId);
}
