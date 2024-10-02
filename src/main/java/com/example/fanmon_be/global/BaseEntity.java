package com.example.fanmon_be.global;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
public class BaseEntity {
    @CreatedDate
    @Column(name="createdat", nullable = false, updatable = false)
    protected LocalDateTime createdat;

    @LastModifiedDate
    @Column(name="updatedat", nullable = false)
    protected LocalDateTime updatedat;

}
