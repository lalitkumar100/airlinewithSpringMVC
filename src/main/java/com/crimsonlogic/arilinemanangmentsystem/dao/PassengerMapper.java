package com.crimsonlogic.arilinemanangmentsystem.dao;

import com.crimsonlogic.arilinemanangmentsystem.model.Passenger;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PassengerMapper {

    /**
     * Inserts a new passenger record into the database.
     */
    @Insert("""
        INSERT INTO `passenger` (
            `passenger_id`, 
            `user_id`, 
            `booking_id`, 
            `first_name`, 
            `last_name`, 
            `date_of_birth`, 
            `gender`, 
            `email`, 
            `phone_number`, 
            `created_at`, 
            `updated_at`, 
            `is_deleted`, 
            `is_cancelled`
        ) VALUES (
            #{passengerId}, 
            #{user.id}, 
            #{booking.bookingId}, 
            #{firstName}, 
            #{lastName}, 
            #{dateOfBirth}, 
            #{gender}, 
            #{email}, 
            #{phoneNumber}, 
            #{createdAt}, 
            #{updatedAt}, 
            #{deleted}, 
            #{isCancelled}
        )
    """)
    int insertPassenger(Passenger passenger);

    /**
     * Retrieves a specific passenger by their unique passenger ID.
     */
    @Select("""
        SELECT 
            p.passenger_id AS passengerId,
            p.user_id AS userId,
            p.booking_id AS bookingId,
            p.first_name AS firstName,
            p.last_name AS lastName,
            p.date_of_birth AS dateOfBirth,
            p.gender AS gender,
            p.email AS email,
            p.phone_number AS phoneNumber,
            p.created_at AS createdAt,
            p.updated_at AS updatedAt,
            p.is_deleted AS deleted,
            p.is_cancelled AS cancelled,
            b.booking_id AS b_bookingId,
            b.booking_datetime AS b_bookingDatetime,
            b.seat_class AS b_seatClass,
            b.amount AS b_amount,
            b.booking_status AS b_bookingStatus
        FROM `passenger` p
        LEFT JOIN `booking` b ON p.booking_id = b.booking_id
        WHERE p.passenger_id = #{passengerId} AND p.is_deleted = 0
    """)
    @Results(id = "PassengerResultMap", value = {
            @Result(property = "passengerId", column = "passengerId"),
            @Result(property = "firstName", column = "firstName"),
            @Result(property = "lastName", column = "lastName"),
            @Result(property = "dateOfBirth", column = "dateOfBirth"),
            @Result(property = "gender", column = "gender"),
            @Result(property = "email", column = "email"),
            @Result(property = "phoneNumber", column = "phoneNumber"),
            @Result(property = "createdAt", column = "createdAt"),
            @Result(property = "updatedAt", column = "updatedAt"),
            @Result(property = "deleted", column = "deleted"),
            @Result(property = "isCancelled", column = "cancelled"),
            @Result(property = "booking.bookingId", column = "b_bookingId"),
            @Result(property = "booking.bookingDateTime", column = "b_bookingDatetime"),
            @Result(property = "booking.seatClass", column = "b_seatClass"),
            @Result(property = "booking.amount", column = "b_amount"),
            @Result(property = "booking.bookingStatus", column = "b_bookingStatus")
    })
    Passenger getPassengerById(String passengerId);

    /**
     * Retrieves all active passengers associated with a specific booking ID.
     */
    @Select("""
        SELECT 
            p.passenger_id AS passengerId,
            p.user_id AS userId,
            p.booking_id AS bookingId,
            p.first_name AS firstName,
            p.last_name AS lastName,
            p.date_of_birth AS dateOfBirth,
            p.gender AS gender,
            p.email AS email,
            p.phone_number AS phoneNumber,
            p.created_at AS createdAt,
            p.updated_at AS updatedAt,
            p.is_deleted AS deleted,
            p.is_cancelled AS cancelled,
            b.booking_id AS b_bookingId,
            b.booking_datetime AS b_bookingDatetime,
            b.seat_class AS b_seatClass,
            b.amount AS b_amount,
            b.booking_status AS b_bookingStatus
        FROM `passenger` p
        LEFT JOIN `booking` b ON p.booking_id = b.booking_id
        WHERE p.booking_id = #{bookingId} AND p.is_deleted = 0
    """)
    @ResultMap("PassengerResultMap")
    List<Passenger> getPassengersByBookingId(String bookingId);

    @Update("UPDATE passenger SET is_cancelled = 1, updated_at = NOW() WHERE passenger_id = #{passengerId}")
    int cancelPassenger(@Param("passengerId") String passengerId);
}