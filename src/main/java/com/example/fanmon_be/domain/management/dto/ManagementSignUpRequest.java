package com.example.fanmon_be.domain.management.dto;

import lombok.Data;

@Data
public class ManagementSignUpRequest {
    private String email;
    private String password;
    private String name;
    private String address;
    private Long businessno;
}
