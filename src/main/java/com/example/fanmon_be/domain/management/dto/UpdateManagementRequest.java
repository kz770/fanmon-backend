package com.example.fanmon_be.domain.management.dto;

import com.example.fanmon_be.global.validation.ValidPassword;
import lombok.Data;

@Data
public class UpdateManagementRequest {
    @ValidPassword
    private String password;
    private String address;
}
