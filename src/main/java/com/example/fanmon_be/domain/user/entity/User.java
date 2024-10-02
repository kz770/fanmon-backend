package com.example.fanmon_be.domain.user.entity;

import com.example.fanmon_be.domain.user.dto.UserResponse;
import com.example.fanmon_be.domain.user.enums.UserRole;
import com.example.fanmon_be.domain.user.enums.UserStatus;
import com.example.fanmon_be.global.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends BaseEntity {
    @Id
    @Column(name = "useruuid", nullable = false)
    private UUID useruuid;

    @PrePersist
    public void generateUUID(){
        if(useruuid == null){
            useruuid = UUID.randomUUID();
        }
        LocalDateTime now = LocalDateTime.now();
        super.createdat = now;
        super.updatedat = now;
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
    @Email(message = "유효한 이메일 주소를 입력하세요.")
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


    public UserResponse toResponse(){
        return new UserResponse(
                this.useruuid,
                this.status.name(),
                this.role.name(),
                this.name,
                this.email,
                this.birth,
                this.phone,
                this.address
        );
    }
}