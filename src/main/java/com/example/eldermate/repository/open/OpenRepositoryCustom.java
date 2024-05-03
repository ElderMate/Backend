package com.example.eldermate.repository.open;

import com.example.eldermate.entity.UserEntity;
import com.example.eldermate.repository.queryDto.OpenQueryDto;

import java.util.List;

public interface OpenRepositoryCustom {
    List<OpenQueryDto> findAllByUser(UserEntity User);
}
