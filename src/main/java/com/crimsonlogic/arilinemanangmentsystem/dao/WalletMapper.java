package com.crimsonlogic.arilinemanangmentsystem.dao;

import com.crimsonlogic.arilinemanangmentsystem.model.Wallet;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@org.springframework.stereotype.Repository
public interface WalletMapper extends org.springframework.data.repository.Repository<Wallet, String> {
    Wallet save(Wallet entity);


    default int insertWallet(Wallet wallet) {
        save(wallet);
        return 1;
    }

    @Query("SELECT w FROM Wallet w JOIN FETCH w.user u WHERE u.id = :userId AND w.deleted = false")
    Wallet getWalletByUserId(@Param("userId") String userId);

    @Transactional
    @Modifying
    @Query("UPDATE Wallet w SET w.balance = w.balance + :amount WHERE w.walletId = :walletId AND w.deleted = false")
    int updateWalletBalance(@Param("walletId") String walletId, @Param("amount") double amount);

    @Query("SELECT CASE WHEN w.balance >= :requiredAmount THEN true ELSE false END FROM Wallet w WHERE w.walletId = :walletId AND w.deleted = false")
    boolean checkBalanceSufficient(@Param("walletId") String walletId, @Param("requiredAmount") double requiredAmount);
}
