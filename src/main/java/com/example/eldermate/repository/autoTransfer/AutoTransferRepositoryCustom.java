package com.example.eldermate.repository.autoTransfer;

import com.example.eldermate.entity.UserEntity;
import com.example.eldermate.repository.queryDto.AutoTransferQueryDto;

import java.util.List;

public interface AutoTransferRepositoryCustom {
    List<AutoTransferQueryDto> findAllByUser(UserEntity User);
}
