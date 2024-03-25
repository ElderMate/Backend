package com.example.eldermate.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class JoinDTO {

    private String username;
    private String password;
    private Integer gender;
    private String name;
    private LocalDate birthdate;

}
