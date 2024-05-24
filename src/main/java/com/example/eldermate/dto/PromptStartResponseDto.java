package com.example.eldermate.dto;

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
