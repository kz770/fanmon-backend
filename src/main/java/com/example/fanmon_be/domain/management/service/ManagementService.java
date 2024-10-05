package com.example.fanmon_be.domain.management.service;

import com.example.fanmon_be.domain.management.dao.ManagementDAO;
import com.example.fanmon_be.domain.management.dto.ManagementResponse;
import com.example.fanmon_be.domain.management.dto.ManagementSignUpRequest;
import com.example.fanmon_be.domain.management.entity.Management;
import com.example.fanmon_be.domain.management.enums.ManagementStatus;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ManagementService {
    @Autowired
    private ManagementDAO managementDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public ManagementResponse signUp(ManagementSignUpRequest signUpRequest){
        if(managementDAO.existsByEmail(signUpRequest.getEmail())){
            throw new IllegalStateException("이미 사용중인 이메일 입니다.");
        }
        if(managementDAO.existsByBusinessno(signUpRequest.getBusinessno())){
            throw new IllegalStateException("이미 등록된 사업자번호 입니다.");
        }

        Management newManagement = Management.builder()
                .status(ManagementStatus.NOT_APPROVED)
                .name(signUpRequest.getName())
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .address(signUpRequest.getAddress())
                .businessno(signUpRequest.getBusinessno())
                .build();

        return managementDAO.save(newManagement).toResponse();
    }
}
