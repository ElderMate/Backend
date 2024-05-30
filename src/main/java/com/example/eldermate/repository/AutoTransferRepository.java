package com.example.eldermate.repository;

import com.example.eldermate.entity.AutoTransfer;
import com.example.eldermate.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AutoTransferRepository extends JpaRepository<AutoTransfer, Long> {

    @Query("SELECT at FROM AutoTransfer at WHERE at.confirm = false AND at.user = :user")
    List<AutoTransfer> findNotConfirmAllByUser(UserEntity user);
}
