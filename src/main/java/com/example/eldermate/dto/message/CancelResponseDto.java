package com.example.eldermate.dto.message;

public record CancelResponseDto(
        String METHOD,
        String LOCATION,
        String TIME,
        String COST
) {
}
