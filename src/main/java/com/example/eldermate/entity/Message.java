package com.example.eldermate.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
//내부 데이터를 뽑고 초기화하기 위해
@Setter
@Getter
@NoArgsConstructor
@ToString
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String pNum;    //발신자 번호

    @Column(nullable = false)
    private String msg;

    @Column(nullable = false)
    private LocalDateTime time;

    @Column
    private Boolean confirm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private UserEntity user;

    @Builder
    public Message(String pNum, String msg, LocalDateTime time, UserEntity user){
        this.pNum = pNum;
        this.msg = msg;
        this.time = time;
        this.user = user;
    }
    
}
