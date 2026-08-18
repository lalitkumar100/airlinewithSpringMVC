package com.crimsonlogic.arilinemanangmentsystem.dao;

import com.crimsonlogic.arilinemanangmentsystem.model.Transaction;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TransactionMapper {

    @Insert("INSERT INTO `transaction` (" +
            "`transaction_id`, `sender_user_id`, `receiver_user_id`, " +
            "`from_payment_method`, `to_payment_method`, `sender_upi`, `receiver_upi`, " +
            "`amount`, `status`, `transaction_time`" +
            ") VALUES (" +
            "#{transactionId}, " +
            "#{sender.id}, " +
            "#{receiver.id}, " +
            "#{fromPaymentMethod, typeHandler=org.apache.ibatis.type.EnumTypeHandler}, " +
            "#{toPaymentMethod, typeHandler=org.apache.ibatis.type.EnumTypeHandler}, " +
            "#{senderUpi}, " +
            "#{receiverUpi}, " +
            "#{amount}, " +
            "#{status, typeHandler=org.apache.ibatis.type.EnumTypeHandler}, " +
            "#{transactionTime}" +
            ")")
    int insertTransaction(Transaction transaction);
}