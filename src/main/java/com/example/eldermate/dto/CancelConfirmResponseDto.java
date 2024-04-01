package com.example.eldermate.dto;

public record CancelConfirmResponseDto(Result result) {

    public static record Result(
            String METHOD,
            String LOCATION,
            String TIME,
            String COST
    ) {
    }
}
