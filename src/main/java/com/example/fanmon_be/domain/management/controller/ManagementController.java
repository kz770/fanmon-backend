package com.example.fanmon_be.domain.management.controller;

import com.example.fanmon_be.domain.management.dto.ManagementResponse;
import com.example.fanmon_be.domain.management.dto.ManagementSignUpRequest;
import com.example.fanmon_be.domain.management.service.ManagementService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/management")
public class ManagementController {
    @Autowired
    ManagementService managementService;

    @Operation(summary = "management 회원가입")
    @PostMapping("/signup")
    public ResponseEntity<ManagementResponse> signUp(@Valid @RequestBody ManagementSignUpRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(managementService.signUp(request));
    }
}
