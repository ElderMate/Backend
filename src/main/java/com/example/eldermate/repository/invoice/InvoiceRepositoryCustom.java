package com.example.eldermate.repository.invoice;

import com.example.eldermate.entity.UserEntity;
import com.example.eldermate.repository.queryDto.InvoiceQueryDto;

import java.util.List;

public interface InvoiceRepositoryCustom {
    List<InvoiceQueryDto> findAllByUser(UserEntity User);
}
