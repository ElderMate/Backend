package com.example.eldermate.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@ToString
// 결제 취소
public class Cancel {

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

    @Builder
    public Cancel(String method, String location, String time, String cost, Message message){
        this.method = method;
        this.location = location;
        this.time = time;
        this.cost = cost;
        this.message = message;
    }

}
