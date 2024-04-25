package com.example.eldermate.repository;

import com.example.eldermate.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("""
    SELECT m
    FROM Message m
    WHERE m.id IN (:ids)
""")
    List<Message> findAllByIds(List<Long> ids);
}

