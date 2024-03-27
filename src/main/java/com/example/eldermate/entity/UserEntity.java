package com.example.eldermate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
//내부 데이터를 뽑고 초기화하기 위해
@Setter
@Getter
@NoArgsConstructor
@ToString
@Table(name="User")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name= "phoneNumber", nullable = false, unique = true)
    private String username;

    private String token;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Integer gender;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate birthdate;

}