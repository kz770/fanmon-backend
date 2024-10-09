package com.example.fanmon_be.domain.user.dto;

import com.example.fanmon_be.domain.user.enums.Role;
import lombok.Data;

import java.util.UUID;

@Data
public class UserDTO {
    private Role role;
    private String name;
    private String email;
    private UUID useruuid;
}
