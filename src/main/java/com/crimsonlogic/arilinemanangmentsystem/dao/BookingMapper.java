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
            #{seatClass},
            #{amount},
            #{bookingStatus},
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
                    column = "seat_class"
            ),

            @Result(
                    property = "amount",
                    column = "amount"
            ),

            @Result(
                    property = "bookingStatus",
                    column = "booking_status"
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
                    property = "flightBooked.flightId",
                    column = "flight_id"
            ),

            @Result(
                    property = "userbooked.id",
                    column = "user_id"
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
}