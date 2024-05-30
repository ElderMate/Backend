package com.example.eldermate.dto.prompt;

public record InvoiceDto(
        Long messageId,
        String payee,
        String cost,
        String time,
        String paymentReason
) { }
