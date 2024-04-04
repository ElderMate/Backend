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
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String payee;

    private String cost;

    private String time;

    private String paymentReason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "msgId", nullable = false)
    private Message message;

}
