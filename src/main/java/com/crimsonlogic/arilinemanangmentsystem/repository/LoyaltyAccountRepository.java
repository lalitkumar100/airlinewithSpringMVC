package com.crimsonlogic.arilinemanangmentsystem.repository;

import com.crimsonlogic.arilinemanangmentsystem.model.LoyaltyAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoyaltyAccountRepository extends JpaRepository<LoyaltyAccount, String> {
}
