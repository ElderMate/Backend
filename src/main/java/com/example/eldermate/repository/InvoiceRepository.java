package com.example.eldermate.repository;

import com.example.eldermate.entity.Invoice;
import com.example.eldermate.entity.UserEntity;
import com.example.eldermate.repository.queryDto.InvoiceQueryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    @Query("""
            SELECT m.id, i.payee, i.cost, i.time, i.paymentReason
            FROM Invoice i 
            INNER JOIN Message m ON m = i.message 
            WHERE m.confirm = false AND m.user = :User""")
    List<InvoiceQueryDto> findAllByUser(UserEntity User);
}