package com.example.eldermate.dto;

public record OpenResponseDto(Result result) {

    public static record Result(
            String BANK,
            String TYPE
    ) {
    }
}

