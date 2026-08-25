package com.crimsonlogic.arilinemanangmentsystem.repository;

import com.crimsonlogic.arilinemanangmentsystem.model.Wallet;
import com.crimsonlogic.arilinemanangmentsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, String> {
    Optional<Wallet> findByUser(User user);
}
