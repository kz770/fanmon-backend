package com.example.fanmon_be.domain.user.entity;

import com.example.fanmon_be.domain.user.enums.Role;
import com.example.fanmon_be.global.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="admin")
@AllArgsConstructor
@NoArgsConstructor
public class Admin extends BaseEntity {

    @Id
    @Column(name = "adminuuid", nullable = false)
    private UUID adminuuid;
    @PrePersist
    public void generateUUID(){
        if(adminuuid == null){
            adminuuid = UUID.randomUUID();
        }
        LocalDateTime now = LocalDateTime.now();
        super.createdat = now;
        super.updatedat = now;
    }

    @Column(name="email", unique = true, nullable = false)
    private String email;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name="role")
    private Role role = Role.ADMIN;


}
