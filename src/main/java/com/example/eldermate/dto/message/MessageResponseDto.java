package com.example.eldermate.dto.message;

import com.example.eldermate.entity.Message;

import java.time.LocalDateTime;

public record MessageResponseDto(
        String pNum,
        String msg,
        LocalDateTime time,
        Boolean confirm,
        Boolean isProblem,
        String problemReason,
        String category
) {
    public static MessageResponseDto from(Message message){
        return new MessageResponseDto(
                message.getPNum(),
                message.getMsg(),
                message.getReceiveTime(),
                message.getConfirm(),
                message.getIsProblem(),
                message.getProblemReason(),
                message.getClass().getSimpleName());
    }
}
