package com.example.fanmon_be.domain.user.service;

import com.example.fanmon_be.domain.user.dao.UserDAO;
import com.example.fanmon_be.domain.user.dto.SignUpRequest;
import com.example.fanmon_be.domain.user.dto.UserResponse;
import com.example.fanmon_be.domain.user.entity.User;
import com.example.fanmon_be.domain.user.enums.UserRole;
import com.example.fanmon_be.domain.user.enums.UserStatus;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse signUp(SignUpRequest signUpRequest) {
        if (userDAO.existsByEmail(signUpRequest.getEmail())) {
            throw new IllegalStateException("이미 사용중인 이메일 입니다.");
        }

        User newUser = User.builder()
                .status(UserStatus.ACTIVE)
                .role(UserRole.NORMAL)
                .name(signUpRequest.getName())
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .phone(signUpRequest.getPhone())
                .address(signUpRequest.getAddress())
                .birth(signUpRequest.getBirth())
                .build();

        return userDAO.save(newUser).toResponse();
    }

    //예은이가 임의로 추가했습니다 필요하시면 지워주세용
    public User getUserById(UUID id){
        return userDAO.findById(id).orElse(null);
    }
}