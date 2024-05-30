package com.example.eldermate.dto.prompt;

public record PromptStartResponseDto(
        String response,
        String fileName
) {
    public static PromptStartResponseDto from(PromptResponseDto responseDto, String fileName){
        return new PromptStartResponseDto(
                responseDto.response(),
                fileName
        );
    }
}
