package com.example.fanmon_be.domain.user.controller;

import com.example.fanmon_be.domain.user.dto.*;
import com.example.fanmon_be.domain.user.entity.User;
import com.example.fanmon_be.domain.user.service.CustomOAuth2UserService;
import com.example.fanmon_be.domain.user.service.UserService;
import com.example.fanmon_be.global.security.UserPrincipal;
import com.example.fanmon_be.global.security.jwt.JwtPlugin;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    CustomOAuth2UserService customOAuth2UserService;

    @Autowired
    JwtPlugin jwtPlugin;

    @Operation(summary = "회원가입")
    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signUp(@Valid @RequestBody SignUpRequest request) {
//        return ResponseEntity.created(URI.create("/login")).body(userService.signUp(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.signUp(request));
    }

    @Operation(summary = "로그인")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login (@RequestBody LoginRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(userService.login(request));
    }

    @Operation(summary = "회원조회")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/myprofile")
    public ResponseEntity<UserResponse> getUser(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        UUID id = userPrincipal.getId();
        return ResponseEntity.status(HttpStatus.OK).body(userService.findById(id));
    }

    @Operation (summary = "회원수정")
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/myprofile")
    public ResponseEntity<UserResponse> updateUser(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @Valid
            @RequestBody UpdateUserRequest request){
        UUID id = userPrincipal.getId();
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(id, request));
    }

    @Operation (summary = "탈퇴")
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/withdraw")
    public ResponseEntity<User> deleteManagement(
            @AuthenticationPrincipal UserPrincipal userPrincipal){
        UUID id = userPrincipal.getId();
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}