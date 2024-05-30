package com.example.eldermate.dto.prompt;

import java.util.List;

public record PromptEndResponseDto(
        List<Long> messageIds,
        List<String> reasons
) {
}
