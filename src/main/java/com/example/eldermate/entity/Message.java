package com.example.eldermate.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
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
    private Boolean confirm = false;

    @Column
    private Boolean isProblem = false;

    @Column
    private String problemReason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private UserEntity user;

    public void setIsProblem(){
        this.isProblem = true;
    }

    public void setConfirm() {this.confirm = true;}

    public void setProblemReason(String reason){
        this.problemReason = reason;
    }
}
