package com.example.eldermate.repository.confirm;

import com.example.eldermate.entity.Cancel;
import com.example.eldermate.entity.Confirm;
import com.example.eldermate.entity.UserEntity;
import com.example.eldermate.repository.queryDto.ConfirmQueryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ConfirmRepository extends JpaRepository<Confirm, Long>, ConfirmRepositoryCustom {
}