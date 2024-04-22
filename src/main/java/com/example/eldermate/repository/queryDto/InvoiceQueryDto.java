package com.example.eldermate.repository.queryDto;

public record InvoiceQueryDto(
        Long messageId,
        String payee,
        String cost,
        String time,
        String paymentReason
) { }
