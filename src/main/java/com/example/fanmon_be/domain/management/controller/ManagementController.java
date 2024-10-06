package com.example.fanmon_be.domain.management.controller;

import com.example.fanmon_be.domain.management.dto.ManagementResponse;
import com.example.fanmon_be.domain.management.dto.ManagementSignUpRequest;
import com.example.fanmon_be.domain.management.dto.UpdateManagementRequest;
import com.example.fanmon_be.domain.management.service.ManagementService;
import com.example.fanmon_be.domain.user.dto.LoginRequest;
import com.example.fanmon_be.domain.user.dto.LoginResponse;
import com.example.fanmon_be.global.security.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/management")
public class ManagementController {
    @Autowired
    ManagementService managementService;

    @Operation(summary = "management 회원가입")
    @PostMapping("/signup")
    public ResponseEntity<ManagementResponse> signUp(@Valid @RequestBody ManagementSignUpRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(managementService.signUp(request));
    }

    @Operation(summary = "management 로그인")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login (@RequestBody LoginRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(managementService.login(request));
    }

    @Operation(summary = "management 회원조회")
    @PreAuthorize("hasRole('MANAGEMENT')")
    @GetMapping("/myprofile")
    public ResponseEntity<ManagementResponse> getManagement(
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        UUID id = userPrincipal.getId();
        return ResponseEntity.status(HttpStatus.OK).body(managementService.findById(id));
    }

    @Operation (summary = "management 회원수정")
    @PreAuthorize("hasRole('MANAGEMENT')")
    @PutMapping("/myprofile")
    public ResponseEntity<ManagementResponse> updateManagement(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @Valid
            @RequestBody UpdateManagementRequest request){
        UUID id = userPrincipal.getId();
        return ResponseEntity.status(HttpStatus.OK).body(managementService.updateManagement(id, request));
    }
}