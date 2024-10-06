package com.example.fanmon_be.domain.management.controller;

import com.example.fanmon_be.domain.management.dto.ManagementResponse;
import com.example.fanmon_be.domain.management.dto.ManagementSignUpRequest;
import com.example.fanmon_be.domain.management.service.ManagementService;
import com.example.fanmon_be.domain.user.dto.LoginRequest;
import com.example.fanmon_be.domain.user.dto.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}