package com.example.eldermate.repository;

import com.example.eldermate.entity.Cancel;
import com.example.eldermate.entity.Confirm;
import com.example.eldermate.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ConfirmRepository extends JpaRepository<Confirm, Long> {
    @Query("""
            SELECT c 
            FROM Confirm c 
            INNER JOIN Message m ON m = c.message 
            WHERE m.confirm = false AND m.user = :User""")
    List<Confirm> findAllByUser(UserEntity User);
}