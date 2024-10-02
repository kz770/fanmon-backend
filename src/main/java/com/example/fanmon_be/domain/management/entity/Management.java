package com.example.fanmon_be.domain.management.entity;

import com.example.fanmon_be.domain.management.dto.ManagementResponse;
import com.example.fanmon_be.domain.management.enums.ManagementStatus;
import com.example.fanmon_be.global.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "management")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Management extends BaseEntity {
    @Id
    @Column(name = "managementuuid", nullable = false)
    private UUID managementuuid;

    @PrePersist
    public void generateUUID() {
        if (managementuuid == null) {
            managementuuid = UUID.randomUUID();
        }
        LocalDateTime now = LocalDateTime.now();
        super.createdat = now;
        super.updatedat = now;
    }

    @Column(name="email", unique = true)
    @Email(message = "유효한 이메일 주소를 입력하세요.")
    private String email;

    private String password;
    private String name;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ManagementStatus status = ManagementStatus.NOT_APPROVED;

    private String address;

    @Size(min=10, max=10, message = "사업자 번호는 10자리 숫자로 구성되어야 합니다.")
    @Pattern(regexp = "^[0-9]+$", message = "사업자 번호는 숫자로만 구성되어야 합니다.")
    @Column(name="businessno", nullable = false, unique=true)
    private String businessno;

    public ManagementResponse toResponse(){
        return new ManagementResponse(
                this.managementuuid,
                this.status.name(),
                this.email,
                this.name,
                this.address,
                this.businessno
        );
    }
}
