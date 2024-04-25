package com.example.eldermate.repository.queryDto;

public record RejectQueryDto(
        Long messageId,
        String method,
        String location,
        String time,
        String cost,
        String rejectReason
) {
}
