package com.crimsonlogic.arilinemanangmentsystem.dao;

import com.crimsonlogic.arilinemanangmentsystem.model.Wallet;
import org.apache.ibatis.annotations.*;

@Mapper
public interface WalletMapper {

    /**
     * Inserts a new wallet record into the database.
     */
    @Insert("""
        INSERT INTO wallet (
            wallet_id, user_id, balance, currency, status, 
            created_at, updated_at, is_deleted
        ) VALUES (
            #{walletId}, #{user.id}, #{balance}, #{currency}, #{status}, 
            #{createdAt}, #{updatedAt}, #{deleted}
        )
    """)
    int insertWallet(Wallet wallet);

    /**
     * Retrieves a wallet along with its associated user details by user ID.
     */
    @Select("""
        SELECT 
            w.wallet_id AS walletId,
            w.balance AS balance,
            w.currency AS currency,
            w.status AS status,
            w.created_at AS createdAt,
            w.updated_at AS updatedAt,
            w.is_deleted AS deleted,
            u.id AS userId,
            u.first_name AS userFirstName,
            u.last_name AS userLastName,
            u.email AS userEmail
        FROM wallet w
        JOIN user u ON w.user_id = u.id
        WHERE w.user_id = #{userId} AND w.is_deleted = 0
    """)
    @Results(id = "WalletWithUserResultMap", value = {
            @Result(property = "walletId", column = "walletId"),
            @Result(property = "balance", column = "balance"),
            @Result(property = "currency", column = "currency"),
            @Result(property = "status", column = "status"),
            @Result(property = "createdAt", column = "createdAt"),
            @Result(property = "updatedAt", column = "updatedAt"),
            @Result(property = "deleted", column = "deleted"),
            @Result(property = "user.id", column = "userId"),
            @Result(property = "user.firstName", column = "userFirstName"),
            @Result(property = "user.lastName", column = "userLastName"),
            @Result(property = "user.email", column = "userEmail")
    })
    Wallet getWalletByUserId(String userId);

    /**
     * Updates/deducts or adds to the wallet balance.
     * Pass a positive amount to add, or a negative amount to subtract.
     */
    @Update("""
        UPDATE wallet 
        SET balance = balance + #{amount}, 
            updated_at = CURRENT_TIMESTAMP 
        WHERE wallet_id = #{walletId} AND is_deleted = 0
    """)
    int updateWalletBalance(@Param("walletId") String walletId, @Param("amount") double amount);

    /**
     * Checks if the wallet has sufficient balance for a specific transaction amount.
     * Returns true if balance >= requiredAmount, otherwise false.
     */
    @Select("""
        SELECT CASE 
            WHEN balance >= #{requiredAmount} THEN 1 
            ELSE 0 
        END 
        FROM wallet 
        WHERE wallet_id = #{walletId} AND is_deleted = 0
    """)
    boolean checkBalanceSufficient(@Param("walletId") String walletId, @Param("requiredAmount") double requiredAmount);
}