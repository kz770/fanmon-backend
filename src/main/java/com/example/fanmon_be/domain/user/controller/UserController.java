package com.example.fanmon_be.domain.user.controller;

import com.example.fanmon_be.domain.user.dto.SignUpRequest;
import com.example.fanmon_be.domain.user.dto.UserResponse;
import com.example.fanmon_be.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @Operation(summary = "회원가입")
    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signUp(@Valid @RequestBody SignUpRequest request) {
        return ResponseEntity.created(URI.create("/login")).body(userService.signUp(request));
//        return ResponseEntity.status(HttpStatus.OK).body(userService.signUp(request));
    }


}
