package com.example.fanmon_be.global.security;

import com.example.fanmon_be.domain.user.dto.CustomOAuth2User;
import com.example.fanmon_be.domain.user.dto.LoginResponse;
import com.example.fanmon_be.global.security.jwt.JwtPlugin;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

@Component
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtPlugin jwtPlugin;

    public CustomSuccessHandler(JwtPlugin jwtPlugin) {
        this.jwtPlugin = jwtPlugin;
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException, IOException {

        //OAuth2User
        CustomOAuth2User customUserDetails = (CustomOAuth2User) authentication.getPrincipal();

        String id = customUserDetails.getId().toString();
        String email = customUserDetails.getEmail();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        String token = jwtPlugin.generateAccessToken(id, email, role);


        // LoginResponse 객체 생성
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAccessToken(token);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");


        String jsonResponse = new ObjectMapper().writeValueAsString(loginResponse);
        response.getWriter().write(jsonResponse);
        response.sendRedirect("http://localhost:3000/user/main?token=" + token);

    }
}
