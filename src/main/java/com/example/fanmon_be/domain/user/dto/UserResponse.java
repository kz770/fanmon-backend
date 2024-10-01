package com.example.fanmon_be.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private UUID useruuid;
    private String status;
    private String role;
    private String email;
    private String name;
    private LocalDate birth;
    private String phone;
    private String address;
}
