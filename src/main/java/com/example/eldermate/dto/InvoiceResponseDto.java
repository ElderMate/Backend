package com.example.eldermate.dto;

public record InvoiceResponseDto (Result result) {

    public static record Result(
            String PAYEE,
            String TIME,
            String COST,
            String PAYMENTREASON

            ) {
    }
}
