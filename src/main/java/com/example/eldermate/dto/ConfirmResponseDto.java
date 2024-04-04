package com.example.eldermate.dto;

public record ConfirmResponseDto(
        String METHOD,
        String LOCATION,
        String TIME,
        String COST
) {
}
