package com.crimsonlogic.arilinemanangmentsystem.dao;

import com.crimsonlogic.arilinemanangmentsystem.model.Refund;
import org.apache.ibatis.annotations.*;

@Mapper
public interface RefundMapper {

    @Insert("""
        INSERT INTO refund (
            refund_id, booking_id, transaction_id, amount, 
            status, reason, refund_time, created_at, 
            updated_at, is_deleted
        ) VALUES (
            #{refundId}, #{booking.bookingId}, #{transaction.transactionId}, #{amount}, 
            #{status, typeHandler=org.apache.ibatis.type.EnumTypeHandler}, #{reason}, #{refundTime}, 
            #{createdAt}, #{updatedAt}, #{deleted}
        )
    """)
    int insertRefund(Refund refund);

    @Results(id = "RefundResultMap", value = {
            @Result(property = "refundId", column = "refund_id", id = true),
            @Result(property = "refundTime", column = "refund_time"),
            @Result(property = "amount", column = "amount"),
            @Result(property = "status", column = "status", javaType = com.crimsonlogic.arilinemanangmentsystem.enumrator.RefundStatus.class, typeHandler = org.apache.ibatis.type.EnumTypeHandler.class),
            @Result(property = "reason", column = "reason"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "updatedAt", column = "updated_at"),
            @Result(property = "deleted", column = "is_deleted")
    })
    @Select("SELECT * FROM refund WHERE booking_id = #{bookingId} AND is_deleted = 0")
    Refund getRefundByBookingId(@Param("bookingId") String bookingId);
}
