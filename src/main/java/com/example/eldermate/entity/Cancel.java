package com.example.eldermate.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@ToString
// 결제 취소
public class Cancel extends Message{

    private String method;

    private String location;

    private String time;

    private String cost;

    @Builder
    public Cancel(String method, String location, String time, String cost){
        this.method = method;
        this.location = location;
        this.time = time;
        this.cost = cost;
    }

}
