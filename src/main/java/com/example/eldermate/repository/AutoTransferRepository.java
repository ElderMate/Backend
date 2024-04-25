package com.example.eldermate.repository;

import com.example.eldermate.entity.AutoTransfer;
import com.example.eldermate.entity.UserEntity;
import com.example.eldermate.repository.queryDto.AutoTransferQueryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AutoTransferRepository extends JpaRepository<AutoTransfer, Long> {
    @Query("""
            SELECT m.id, a.bank, a.company
            FROM AutoTransfer a
            INNER JOIN Message m ON m = a.message
            WHERE m.confirm = false AND m.user = :User""")
    List<AutoTransferQueryDto> findAllByUser(UserEntity User);
}
