package com.example.eldermate.repository;

import com.example.eldermate.entity.Invoice;
import com.example.eldermate.entity.NonPayment;
import com.example.eldermate.entity.UserEntity;
import com.example.eldermate.repository.queryDto.NonPaymentQueryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NonPaymentRepository extends JpaRepository<NonPayment, Long> {
    @Query("""
            SELECT m.id, n.payee, n.cost, n.time
            FROM NonPayment n
            INNER JOIN Message m ON m = n.message 
            WHERE m.confirm = false AND m.user = :User""")
    List<NonPaymentQueryDto> findAllByUser(UserEntity User);
}
