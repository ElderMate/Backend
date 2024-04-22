package com.example.eldermate.repository;

import com.example.eldermate.entity.Invoice;
import com.example.eldermate.entity.Open;
import com.example.eldermate.entity.UserEntity;
import com.example.eldermate.repository.queryDto.OpenQueryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OpenRepository extends JpaRepository<Open, Long> {
    @Query("""
            SELECT m.id, o.bank, o.type
            FROM Open o
            INNER JOIN Message m ON m = o.message 
            WHERE m.confirm = false AND m.user = :User""")
    List<OpenQueryDto> findAllByUser(UserEntity User);
}
