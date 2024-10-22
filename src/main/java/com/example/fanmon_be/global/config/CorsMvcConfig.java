package com.example.fanmon_be.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;


@Configuration
public class CorsMvcConfig {

    @Value("${spring.web.cors.allowed-origins}")
    private String allowedOrigins;


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(allowedOrigins.split(","))); // ,로 구분된 다수의 도메인 지원
        configuration.setAllowedMethods(Arrays.asList("*")); // 허용할 메서드
        configuration.setAllowedHeaders(Arrays.asList("*")); // 허용할 헤더
        configuration.setAllowCredentials(true); // 인증 정보 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 모든 경로에 CORS 설정 등록
        return source;
    }
}
