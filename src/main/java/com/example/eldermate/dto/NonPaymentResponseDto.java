package com.example.eldermate.dto;

public record NonPaymentResponseDto(Result result) {

    public static record Result(
            String PAYEE,
            String COST,
            String TIME
    ) {
    }
}
