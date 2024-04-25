package com.example.eldermate.repository;

import com.example.eldermate.entity.Cancel;
import com.example.eldermate.entity.UserEntity;
import com.example.eldermate.repository.queryDto.CancelQueryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CancelRepository extends JpaRepository<Cancel, Long> {
    @Query("""
            SELECT m.id, c.method, c.location, c.time, c.cost
            FROM Cancel c 
            INNER JOIN Message m ON m = c.message
            WHERE m.confirm = false AND m.user = :User""")
    List<CancelQueryDto> findAllByUser(UserEntity User);
}
