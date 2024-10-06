package com.example.fanmon_be.domain.user.service;

import com.example.fanmon_be.domain.user.dao.UserDAO;
import com.example.fanmon_be.domain.user.dto.LoginRequest;
import com.example.fanmon_be.domain.user.dto.LoginResponse;
import com.example.fanmon_be.domain.user.dto.SignUpRequest;
import com.example.fanmon_be.domain.user.dto.UserResponse;
import com.example.fanmon_be.domain.user.entity.User;
import com.example.fanmon_be.domain.user.enums.Role;
import com.example.fanmon_be.domain.user.enums.UserStatus;
import com.example.fanmon_be.global.exception.ModelNotFoundException;
import com.example.fanmon_be.global.security.jwt.JwtPlugin;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtPlugin jwtPlugin;

    @Transactional
    public UserResponse signUp(SignUpRequest signUpRequest) {
        if (userDAO.existsByEmail(signUpRequest.getEmail())) {
            throw new IllegalStateException("이미 사용중인 이메일 입니다.");
        }

        User newUser = User.builder()
                .status(UserStatus.ACTIVE)
                .role(Role.USER)
                .name(signUpRequest.getName())
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .phone(signUpRequest.getPhone())
                .address(signUpRequest.getAddress())
                .birth(signUpRequest.getBirth())
                .build();

        return userDAO.save(newUser).toResponse();
    }

    public LoginResponse login(LoginRequest request) throws Exception {
        User user = userDAO.findByEmail(request.getEmail())
                .orElseThrow(() -> new ModelNotFoundException(request.getEmail()));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new Exception("password 불일치");
        }

        String accessToken = jwtPlugin.generateAccessToken(
                user.getUseruuid().toString(),
                user.getEmail(),
                user.getRole().toString()
        );

        return new LoginResponse(
                accessToken
        );
    }
}