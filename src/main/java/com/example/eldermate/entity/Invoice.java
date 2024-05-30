package com.example.eldermate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor
@SuperBuilder
// 납부 예정
public class Invoice extends  Message{
    private String payee;

    private String cost;

    private String invoiceTime;

    private String paymentReason;
}
