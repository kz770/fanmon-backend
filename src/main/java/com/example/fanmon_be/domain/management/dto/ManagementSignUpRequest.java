package com.example.fanmon_be.domain.management.dto;

import com.example.fanmon_be.global.validation.ValidPassword;
import lombok.Data;

@Data
public class ManagementSignUpRequest {
    private String email;
    @ValidPassword
    private String password;
    private String name;
    private String address;
    private String businessno;
}
