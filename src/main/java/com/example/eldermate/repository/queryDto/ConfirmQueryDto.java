package com.example.eldermate.repository.queryDto;

public record ConfirmQueryDto(
        Long messageId,
        String method,
        String location,
        String time,
        String cost
) { }
