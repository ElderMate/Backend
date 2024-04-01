package com.example.eldermate.repository;

import com.example.eldermate.entity.NonPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NonPaymentRepository extends JpaRepository<NonPayment, Long> {
}
