package com.example.fanmon_be.domain.user.entity;

import com.example.fanmon_be.domain.user.dto.UserResponse;
import com.example.fanmon_be.domain.user.enums.Role;
import com.example.fanmon_be.domain.user.enums.UserStatus;
import com.example.fanmon_be.global.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class  User extends BaseEntity {
    @Id
    @Column(name = "useruuid")
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
    private Role role = Role.USER;

    @Column(name="name")
    private String name;

    @Column(name="email", unique = true)
    @Email(message = "유효한 이메일 주소를 입력하세요.")
    private String email;

    @Column(name="password")
    private String password;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "birth")
    private LocalDate birth;

    @Size(min = 5, max = 5, message = "우편번호는 5자리 숫자로 구성되어야 합니다.")
    @Pattern(regexp = "^[0-9]{5}$", message = "우편번호는 숫자로만 구성되어야 하며, 5자리여야 합니다.")
    private String postcode;


    public UserResponse toResponse(){
        return new UserResponse(
                this.useruuid,
                this.status.name(),
                this.role.name(),
                this.email,
                this.name,
                this.birth,
                this.phone,
                this.address,
                this.postcode
        );
    }
}