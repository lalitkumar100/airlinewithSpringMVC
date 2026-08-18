package com.crimsonlogic.arilinemanangmentsystem.dao;

import com.crimsonlogic.arilinemanangmentsystem.model.Refund;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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

    @Select("SELECT * FROM refund WHERE booking_id = #{bookingId} AND is_deleted = 0")
    Refund getRefundByBookingId(@Param("bookingId") String bookingId);
}
