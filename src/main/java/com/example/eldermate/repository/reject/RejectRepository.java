package com.example.eldermate.repository.reject;

import com.example.eldermate.entity.Open;
import com.example.eldermate.entity.Reject;
import com.example.eldermate.entity.UserEntity;
import com.example.eldermate.repository.queryDto.RejectQueryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RejectRepository extends JpaRepository<Reject, Long>, RejectRepositoryCustom{
}
