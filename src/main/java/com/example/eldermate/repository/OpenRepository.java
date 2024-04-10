package com.example.eldermate.repository;

import com.example.eldermate.entity.Invoice;
import com.example.eldermate.entity.Open;
import com.example.eldermate.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OpenRepository extends JpaRepository<Open, Long> {
    @Query("""
            SELECT o 
            FROM Open o
            INNER JOIN Message m ON m = o.message 
            WHERE m.confirm = false AND m.user = :User""")
    List<Open> findAllByUser(UserEntity User);
}
