package com.example.eldermate.dto;

import java.util.List;

public record ProblemRequestDto(
        List<Long> messageIds
) {
}
