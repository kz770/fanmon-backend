package com.example.fanmon_be.domain.user.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SignUpRequest {
    private String email;
    private String password;
    private String name;
    private LocalDate birth;
    private String phone;
    private String address;
}
