package com.crimsonlogic.arilinemanangmentsystem.dao;

import com.crimsonlogic.arilinemanangmentsystem.model.Booking;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BookingMapper {

    // Insert booking
    @Insert("""
        INSERT INTO booking
        (
            booking_id,
            flight_id,
            booking_datetime,
            seat_class,
            amount,
            booking_status,
            user_id
        )
        VALUES
        (
            #{bookingId},
            #{flightBooked.flightId},
            #{bookingDateTime},
            #{seatClass, typeHandler=org.apache.ibatis.type.EnumTypeHandler},
            #{amount},
            #{bookingStatus, typeHandler=org.apache.ibatis.type.EnumTypeHandler},
            #{userbooked.id}
        )
        """)
    int insertBooking(Booking booking);


    // Get booking by booking ID
    @Select("""
        SELECT
            booking_id,
            flight_id,
            booking_datetime,
            seat_class,
            amount,
            booking_status,
            created_at,
            updated_at,
            is_deleted,
            user_id
        FROM booking
        WHERE booking_id = #{bookingId}
          AND is_deleted = 0
        """)
    @Results(id = "BookingResultMap", value = {

            @Result(
                    property = "bookingId",
                    column = "booking_id",
                    id = true
            ),

            @Result(
                    property = "bookingDateTime",
                    column = "booking_datetime"
            ),

            @Result(
                    property = "seatClass",
                    column = "seat_class",
                    javaType = com.crimsonlogic.arilinemanangmentsystem.enumrator.SeatClass.class,
                    typeHandler = org.apache.ibatis.type.EnumTypeHandler.class
            ),

            @Result(
                    property = "amount",
                    column = "amount"
            ),

            @Result(
                    property = "bookingStatus",
                    column = "booking_status",
                    javaType = com.crimsonlogic.arilinemanangmentsystem.enumrator.BookingStatus.class,
                    typeHandler = org.apache.ibatis.type.EnumTypeHandler.class
            ),

            @Result(
                    property = "createdAt",
                    column = "created_at"
            ),

            @Result(
                    property = "updatedAt",
                    column = "updated_at"
            ),

            @Result(
                    property = "deleted",
                    column = "is_deleted"
            ),

            @Result(
                    property = "flightBooked",
                    column = "flight_id",
                    one = @One(select = "com.crimsonlogic.arilinemanangmentsystem.dao.FlightMapper.findById")
            ),

            @Result(
                    property = "userbooked",
                    column = "user_id",
                    one = @One(select = "com.crimsonlogic.arilinemanangmentsystem.dao.UserMapper.findById")
            )
    })
    Booking getBookingById(String bookingId);


    // Get all bookings of a particular user
    @Select("""
        SELECT
            booking_id,
            flight_id,
            booking_datetime,
            seat_class,
            amount,
            booking_status,
            created_at,
            updated_at,
            is_deleted,
            user_id
        FROM booking
        WHERE user_id = #{userId}
          AND is_deleted = 0
        ORDER BY booking_datetime DESC
        """)
    @ResultMap("BookingResultMap")
    List<Booking> getAllBookingsByUserId(String userId);

    @Select("""
    SELECT COUNT(p.passenger_id)
    FROM passenger p
    JOIN booking b ON p.booking_id = b.booking_id
    WHERE b.flight_id = #{flightId}
      AND b.seat_class = #{seatClass, typeHandler=org.apache.ibatis.type.EnumTypeHandler}
      AND b.is_deleted = 0
      AND p.is_cancelled = 0
      AND b.booking_status IN (
          'CONFIRMED',
          'CONFIRMED_NOT_CHECKED_IN',
          'CHECKED_IN'
      )
    """)
    int getBookedSeatCount(
            @Param("flightId") String flightId,
            @Param("seatClass")
            com.crimsonlogic.arilinemanangmentsystem.enumrator.SeatClass seatClass
    );

    @Update("""
        UPDATE booking 
        SET booking_status = #{status, typeHandler=org.apache.ibatis.type.EnumTypeHandler}, 
            updated_at = NOW() 
        WHERE booking_id = #{bookingId} 
          AND is_deleted = 0
        """)
    int updateBookingStatus(@Param("bookingId") String bookingId, @Param("status") com.crimsonlogic.arilinemanangmentsystem.enumrator.BookingStatus status);

    @Select("""
        SELECT
            booking_id,
            flight_id,
            booking_datetime,
            seat_class,
            amount,
            booking_status,
            created_at,
            updated_at,
            is_deleted,
            user_id
        FROM booking
        WHERE flight_id = #{flightId}
          AND is_deleted = 0
        """)
    @ResultMap("BookingResultMap")
    List<Booking> getBookingsByFlightId(@Param("flightId") String flightId);
}