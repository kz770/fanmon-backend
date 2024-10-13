package com.example.fanmon_be.domain.management.service;

import com.example.fanmon_be.domain.management.dao.ManagementDAO;
import com.example.fanmon_be.domain.management.dto.ManagementResponse;
import com.example.fanmon_be.domain.management.dto.ManagementSignUpRequest;
import com.example.fanmon_be.domain.management.dto.UpdateManagementRequest;
import com.example.fanmon_be.domain.management.entity.Management;
import com.example.fanmon_be.domain.management.enums.ManagementStatus;
import com.example.fanmon_be.domain.user.dto.LoginRequest;
import com.example.fanmon_be.domain.user.dto.LoginResponse;
import com.example.fanmon_be.domain.user.enums.Role;
import com.example.fanmon_be.global.exception.EmailAlreadyExistsException;
import com.example.fanmon_be.global.exception.ModelNotFoundException;
import com.example.fanmon_be.global.security.jwt.JwtPlugin;
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
    @Autowired
    private JwtPlugin jwtPlugin;

    @Transactional
    public ManagementResponse signUp(ManagementSignUpRequest signUpRequest) {
        if (managementDAO.existsByEmail(signUpRequest.getEmail())) {
            throw new IllegalStateException("이미 사용중인 이메일 입니다.");
        }
        if (managementDAO.existsByBusinessno(signUpRequest.getBusinessno())) {
            throw new IllegalStateException("이미 등록된 사업자번호 입니다.");
        }

        Management newManagement = Management.builder()
                .status(ManagementStatus.NOT_APPROVED)
                .role(Role.MANAGEMENT)
                .name(signUpRequest.getName())
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .address(signUpRequest.getAddress())
                .businessno(signUpRequest.getBusinessno())
                .build();

        return managementDAO.save(newManagement).toResponse();
    }

    public LoginResponse login(LoginRequest request) throws Exception {
        Management management = managementDAO.findByEmail(request.getEmail())
                .orElseThrow(() -> new ModelNotFoundException(request.getEmail()));

        if(!passwordEncoder.matches(request.getPassword(), management.getPassword())){
            throw new Exception("password 불일치");
        }

        String accessToken = jwtPlugin.generateAccessToken(
                management.getManagementuuid().toString(),
                management.getEmail(),
                management.getRole().toString()
        );

        return new LoginResponse(
                accessToken
//                management.getRole()
        );
    }

    public ManagementResponse findById(UUID managementuuid) {
        Management management = managementDAO.findById(managementuuid)
                .orElseThrow(() -> new ModelNotFoundException(managementuuid.toString()));
        return management.toResponse();
    }

    public ManagementResponse updateManagement(UUID managementuuid, UpdateManagementRequest request){
        Management management = managementDAO.findById(managementuuid)
                .orElseThrow(() -> new ModelNotFoundException(managementuuid.toString()));
        management.setPassword(passwordEncoder.encode(request.getPassword()));
        management.setAddress(request.getAddress());
        return managementDAO.save(management).toResponse();
    }

    public void deleteManagement(UUID managementuuid){
        if (!managementDAO.existsById(managementuuid)) {
            throw new ModelNotFoundException(managementuuid.toString());
        }
        managementDAO.deleteById(managementuuid);
    }

    public boolean checkEmail(String email){
        if(managementDAO.existsByEmail(email)){
            throw new EmailAlreadyExistsException();
        }
        return false;
    }
}