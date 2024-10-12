package com.example.fanmon_be.domain.user.dto;

import com.example.fanmon_be.domain.user.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String accessToken;
    private Role role;
}
