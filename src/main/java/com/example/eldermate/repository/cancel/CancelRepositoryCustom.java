package com.example.eldermate.repository.cancel;

import com.example.eldermate.entity.UserEntity;
import com.example.eldermate.repository.queryDto.AutoTransferQueryDto;
import com.example.eldermate.repository.queryDto.CancelQueryDto;

import java.util.List;

public interface CancelRepositoryCustom {
    List<CancelQueryDto> findAllByUser(UserEntity User);
}
