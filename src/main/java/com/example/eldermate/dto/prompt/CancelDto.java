package com.example.eldermate.dto.prompt;

public record CancelDto(
        Long messageId,
        String method,
        String location,
        String time,
        String cost
) { }
