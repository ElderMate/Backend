package com.example.eldermate.dto.message;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class MessageDTO {
    private String pNum;
    private String msg;
    private LocalDateTime time;

    public MessageDTO(String pNum, String msg, LocalDateTime time) {
        this.pNum = pNum;
        this.msg = msg;
        this.time = time;
    }
}
