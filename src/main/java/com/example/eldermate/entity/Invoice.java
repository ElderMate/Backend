package com.example.eldermate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@NoArgsConstructor
@ToString
// 납부 예정
public class Invoice extends  Message{
    private String payee;

    private String cost;

    private String time;

    private String paymentReason;
}
