package com.example.eldermate.repository.reject;

import com.example.eldermate.entity.UserEntity;
import com.example.eldermate.repository.queryDto.RejectQueryDto;

import java.util.List;

public interface RejectRepositoryCustom {
    List<RejectQueryDto> findAllByUser(UserEntity User);
}
