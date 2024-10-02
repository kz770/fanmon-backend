package com.example.fanmon_be.domain.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManagementResponse {
    private UUID managementuuid;
    private String status;
    private String email;
    private String name;
    private String address;
    private Long businessno;
}
