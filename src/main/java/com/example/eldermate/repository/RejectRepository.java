package com.example.eldermate.repository;

import com.example.eldermate.entity.Open;
import com.example.eldermate.entity.Reject;
import com.example.eldermate.entity.UserEntity;
import com.example.eldermate.repository.queryDto.RejectQueryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RejectRepository extends JpaRepository<Reject, Long> {
    @Query("""
            SELECT m.id, r.method, r.location, r.time, r.cost, r.rejectReason
            FROM Reject r
            INNER JOIN Message m ON m = r.message 
            WHERE m.confirm = false AND m.user = :User""")
    List<RejectQueryDto> findAllByUser(UserEntity User);
}
