package com.example.eldermate.dto;

public record InvoiceResponseDto (
        String PAYEE,
        String TIME,
        String COST,
        String PAYMENTREASON) {

}
