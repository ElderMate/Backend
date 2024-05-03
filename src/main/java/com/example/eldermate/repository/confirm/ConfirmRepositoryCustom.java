package com.example.eldermate.repository.confirm;

import com.example.eldermate.entity.UserEntity;
import com.example.eldermate.repository.queryDto.CancelQueryDto;
import com.example.eldermate.repository.queryDto.ConfirmQueryDto;

import java.util.List;

public interface ConfirmRepositoryCustom {
    List<ConfirmQueryDto> findAllByUser(UserEntity User);
}
