package com.example.eldermate.dto;

public record CancelResponseDto(
        String METHOD,
        String LOCATION,
        String TIME,
        String COST
) {
}
