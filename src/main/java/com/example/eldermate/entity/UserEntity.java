package com.example.eldermate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
//내부 데이터를 뽑고 초기화하기 위해
@Setter
@Getter
@NoArgsConstructor
@ToString
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name= "phoneNumber")
    private String username;

    private String password;

    private String role;

    public Boolean isAdmin(){
        return role.equals("ROLE_ADMIN");
    }
}