package com.example.eldermate.dto;

public record RejectResponseDto(
        String METHOD,
        String LOCATION,
        String TIME,
        String COST,
        String RejectionReason
) {
}

