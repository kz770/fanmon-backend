package com.example.fanmon_be.domain.management.entity;

import com.example.fanmon_be.domain.management.enums.ManagementStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "management")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Management {
    @Id
    @Column(name = "managementuuid", nullable = false)
    private UUID managementuuid;

    @PrePersist
    public void generateUUID() {
        if (managementuuid == null) {
            managementuuid = UUID.randomUUID();
        }
    }
    private String email;
    private String password;
    private String name;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ManagementStatus status = ManagementStatus.NOT_APPROVED;

    private String address;
    private Long businessno;
    private LocalDateTime createdat;
    private LocalDateTime updatedat;
}
