package com.example.eldermate.repository.queryDto;

public record NonPaymentQueryDto(
        Long messageId,
        String payee,
        String cost,
        String time
) { }
