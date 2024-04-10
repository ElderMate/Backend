package com.example.eldermate.repository;

import com.example.eldermate.entity.Confirm;
import com.example.eldermate.entity.Invoice;
import com.example.eldermate.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    @Query("""
            SELECT i 
            FROM Invoice i 
            INNER JOIN Message m ON m = i.message 
            WHERE m.confirm = false AND m.user = :User""")
    List<Invoice> findAllByUser(UserEntity User);
}