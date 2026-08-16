package com.crimsonlogic.arilinemanangmentsystem.dao;

import com.crimsonlogic.arilinemanangmentsystem.model.Wallet;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WalletMapper {

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
}