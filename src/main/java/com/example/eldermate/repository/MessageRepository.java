package com.example.eldermate.repository;

import com.example.eldermate.dto.message.MessageResponseDto;
import com.example.eldermate.entity.Message;
import com.example.eldermate.entity.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("SELECT m FROM Message m WHERE m.id IN (:ids)")
    List<Message> findAllByIds(List<Long> ids);

    List<Message> findAllByUser(UserEntity user);

    @Query("""
    select new com.example.eldermate.dto.message.MessageResponseDto(
        m.pNum,
        m.msg,
        m.receiveTime,
        m.confirm,
        m.isProblem,
        m.problemReason,
        m.dtype
    )
    from Message m
    where m.user = :user
    order by m.receiveTime desc
""")
    Slice<MessageResponseDto> findAllDTOByUser(UserEntity user, Pageable pageable);

    @Query("SELECT m FROM Message m WHERE m.confirm != true AND m.user = :user")
    List<Message> findNotConfirmAllByUser(UserEntity user);
}


