package com.example.eldermate.repository.cancel;

import com.example.eldermate.entity.Cancel;
import com.example.eldermate.entity.UserEntity;
import com.example.eldermate.repository.queryDto.CancelQueryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CancelRepository extends JpaRepository<Cancel, Long>, CancelRepositoryCustom {
}
