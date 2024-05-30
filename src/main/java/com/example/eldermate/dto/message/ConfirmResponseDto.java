package com.example.eldermate.dto.message;

public record ConfirmResponseDto(
        String METHOD,
        String LOCATION,
        String TIME,
        String COST
) {
}
