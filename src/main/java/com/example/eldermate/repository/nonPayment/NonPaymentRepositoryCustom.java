package com.example.eldermate.repository.nonPayment;

import com.example.eldermate.entity.UserEntity;
import com.example.eldermate.repository.queryDto.NonPaymentQueryDto;

import java.util.List;

public interface NonPaymentRepositoryCustom {
    List<NonPaymentQueryDto> findAllByUser(UserEntity User);
}
