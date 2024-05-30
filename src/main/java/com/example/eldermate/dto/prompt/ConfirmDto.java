package com.example.eldermate.dto.prompt;

public record ConfirmDto(
        Long messageId,
        String method,
        String location,
        String time,
        String cost
) { }
