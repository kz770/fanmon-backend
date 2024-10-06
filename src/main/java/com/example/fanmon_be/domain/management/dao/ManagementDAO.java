package com.example.fanmon_be.domain.management.dao;

import com.example.fanmon_be.domain.management.entity.Management;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ManagementDAO extends JpaRepository<Management, Integer> {
    boolean existsByEmail(String email);
    boolean existsByBusinessno(String businessno);

    public Management findByManagementuuid(UUID managementuuid);
}
