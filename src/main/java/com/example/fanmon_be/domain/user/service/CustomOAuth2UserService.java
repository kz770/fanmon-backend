package com.example.fanmon_be.domain.user.service;

import com.example.fanmon_be.domain.user.dao.UserDAO;
import com.example.fanmon_be.domain.user.dto.CustomOAuth2User;
import com.example.fanmon_be.domain.user.dto.GoogleResponse;
import com.example.fanmon_be.domain.user.dto.OAuth2Response;
import com.example.fanmon_be.domain.user.dto.UserDTO;
import com.example.fanmon_be.domain.user.entity.User;
import com.example.fanmon_be.domain.user.enums.Role;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserDAO userDAO;

    public CustomOAuth2UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;
        if (registrationId.equals("google")) {
            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        } else {
            return null;
        }

        String email = oAuth2Response.getEmail();
        Optional<User> existData = userDAO.findByEmail(email);

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(oAuth2Response.getEmail());
        userDTO.setName(oAuth2Response.getName());
        userDTO.setRole(Role.USER);

        if (existData.isEmpty()) {
            // 새로운 사용자 등록 시 UUID 생성
            UUID userUUID = UUID.randomUUID();
            userDTO.setUseruuid(userUUID);  // UUID를 설정
            User userEntity = new User();
            userEntity.setUseruuid(userUUID); // UUID를 사용자 엔티티에 설정
            userEntity.setEmail(oAuth2Response.getEmail());
            userEntity.setName(oAuth2Response.getName());
            userEntity.setRole(Role.USER);
            userDAO.save(userEntity);
        } else {
            User existData2 = existData.get();
            userDTO.setUseruuid(existData2.getUseruuid()); // 기존 사용자 UUID 설정
            existData2.setEmail(oAuth2Response.getEmail());
            existData2.setName(oAuth2Response.getName());
            userDAO.save(existData2);
        }
        return new CustomOAuth2User(userDTO);
    }
}

