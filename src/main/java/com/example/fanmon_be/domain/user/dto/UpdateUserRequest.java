package com.example.fanmon_be.domain.user.dto;

import com.example.fanmon_be.global.validation.ValidPassword;
import com.example.fanmon_be.global.validation.ValidPhone;
import lombok.Data;

@Data
public class UpdateUserRequest {
    @ValidPassword
    private String password;
    @ValidPhone
    private String phone;
    private String address;
}
