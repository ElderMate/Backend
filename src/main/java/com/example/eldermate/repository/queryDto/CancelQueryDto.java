package com.example.eldermate.repository.queryDto;

public record CancelQueryDto(
        Long messageId,
        String method,
        String location,
        String time,
        String cost
) { }
