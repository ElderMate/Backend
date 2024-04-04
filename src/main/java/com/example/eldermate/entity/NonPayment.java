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
// 미납
public class NonPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String payee;

    private String cost;

    private String time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "msgId", nullable = false)
    private Message message;
}
