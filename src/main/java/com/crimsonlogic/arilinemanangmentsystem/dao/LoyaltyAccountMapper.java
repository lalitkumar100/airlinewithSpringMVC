package com.crimsonlogic.arilinemanangmentsystem.dao;

import com.crimsonlogic.arilinemanangmentsystem.model.LoyaltyAccount;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LoyaltyAccountMapper {

    @Insert("""
        INSERT INTO loyalty_account (
            loyalty_account_id, user_id, points, tier, 
            created_at, updated_at, is_deleted
        ) VALUES (
            #{loyaltyAccountId}, #{userId}, #{account.points}, 
            #{account.tier, typeHandler=org.apache.ibatis.type.EnumTypeHandler}, 
            #{account.createdAt}, #{account.updatedAt}, #{account.deleted}
        )
    """)
     int insertLoyaltyAccount(
            @Param("loyaltyAccountId") String loyaltyAccountId,
            @Param("userId") String userId,
            @Param("account") LoyaltyAccount account
    );
}