package com.example.eldermate.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@SuperBuilder
// 결제 승인
public class Confirm extends Message {
    private String method;

    private String location;

    private String confirmTime;

    private String cost;
}
