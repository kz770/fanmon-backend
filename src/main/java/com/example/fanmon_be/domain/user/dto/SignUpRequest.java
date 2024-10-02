package com.example.fanmon_be.domain.user.dto;

import com.example.fanmon_be.global.validation.ValidPassword;
import com.example.fanmon_be.global.validation.ValidPhone;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SignUpRequest {
    private String email;
    @ValidPassword
    private String password;
    private String name;
    private LocalDate birth;
    @ValidPhone
    private String phone;
    private String address;
}
