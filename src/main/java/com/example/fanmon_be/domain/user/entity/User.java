package com.example.fanmon_be.domain.user.entity;

import com.example.fanmon_be.domain.user.enums.UserRole;
import com.example.fanmon_be.domain.user.enums.UserStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="user")
@Data
public class User {
    @Id
    @Column(name = "useruuid", nullable = false)
    private UUID useruuid;

    @PrePersist
    public void generateUUID(){
        if(useruuid == null){
            useruuid = UUID.randomUUID();
        }
    }

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus status = UserStatus.ACTIVE;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.NORMAL;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="email", nullable = true, unique = true)
    private String email;

    //추후 소셜로그인 기능 구현 위해 nullable true 로 했습니다
    @Column(name="password", nullable = true)
    private String password;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "birth", nullable = false)
    private LocalDate birth;

    @Column(name="createdat", nullable = false, updatable = false)
    private LocalDateTime createdat;

    @Column(name="updatedat", nullable = false)
    private LocalDateTime updatedat;
}