package com.example.fanmon_be.domain.management.dao;

import com.example.fanmon_be.domain.management.entity.Management;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagementDAO extends JpaRepository<Management, Integer> {
    boolean existsByEmail(String email);
}
