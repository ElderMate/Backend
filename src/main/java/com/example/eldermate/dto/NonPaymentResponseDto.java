package com.example.eldermate.dto;

public record NonPaymentResponseDto(
        String PAYEE,
        String COST,
        String TIME
) {
}
