package com.example.eldermate.dto.message;

public record InvoiceResponseDto (
        String PAYEE,
        String TIME,
        String COST,
        String PAYMENTREASON) {

}
