package com.example.eldermate.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor
@SuperBuilder
// 결제 취소
public class Cancel extends Message{

    private String method;

    private String location;

    private String cancelTime;

    private String cost;

}
