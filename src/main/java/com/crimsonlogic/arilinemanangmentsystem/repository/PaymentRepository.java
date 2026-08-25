package com.crimsonlogic.arilinemanangmentsystem.repository;

import com.crimsonlogic.arilinemanangmentsystem.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {
}
