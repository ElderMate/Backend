package com.example.eldermate.repository;

import com.example.eldermate.entity.Invoice;
import com.example.eldermate.entity.Open;
import com.example.eldermate.entity.UserEntity;
import com.example.eldermate.repository.open.OpenRepositoryCustom;
import com.example.eldermate.repository.queryDto.OpenQueryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OpenRepository extends JpaRepository<Open, Long> {
    @Query("SELECT o FROM Open o WHERE o.confirm = false AND o.user = :user")
    List<Open> findNotConfirmAllByUser(UserEntity User);
}
