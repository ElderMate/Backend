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
// 결제 승인
public class Confirm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String method;

    private String location;

    private String time;

    private String cost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "msgId", nullable = false)
    private Message message;

}
