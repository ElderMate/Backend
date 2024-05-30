package com.example.eldermate.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
//내부 데이터를 뽑고 초기화하기 위해
@Getter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@SuperBuilder // Lombok 빌더 확장
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String pNum;    //발신자 번호

    @Column(nullable = false)
    private String msg;

    @Column(nullable = false)
    private LocalDateTime receiveTime;

    @Column
    private Boolean confirm;

    @Column
    private Boolean isProblem;

    @Column
    private String problemReason;

    @Column
    private String category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private UserEntity user;

    public void setIsProblem(){
        this.isProblem = true;
    }

    public void setConfirm() {this.confirm = true;}
    
}
