package com.example.eldermate.dto;

public record CancelConfirmResponseDto(
        String METHOD,
        String LOCATION,
        String TIME,
        String COST
) {
}
