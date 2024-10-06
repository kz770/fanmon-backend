package com.example.fanmon_be.global.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtPlugin {

    private final String issuer;
    private final String secret;
    private final long accessTokenExpirationHour;

    public JwtPlugin(
            @Value("${auth.jwt.issuer}") String issuer,
            @Value("${auth.jwt.secret}") String secret,
            @Value("${auth.jwt.accessTokenExpirationHour}") long accessTokenExpirationHour) {
        this.issuer = issuer;
        this.secret = secret;
        this.accessTokenExpirationHour = accessTokenExpirationHour;
    }

    public Jws<Claims> validateToken(String jwt) {
        try {
            var key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
            return Jwts.parser().verifyWith(key).build().parseSignedClaims(jwt);
        } catch (Exception e) {
            throw new RuntimeException("Invalid JWT token", e);
        }
    }

    public String generateAccessToken(String subject, String email, String role) {
        return generateToken(subject, email, role, Duration.ofHours(accessTokenExpirationHour));
    }

    private String generateToken(String subject, String email, String role, Duration expirationPeriod) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        claims.put("email", email);

        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        Instant now = Instant.now();
        return Jwts.builder()
                .setSubject(subject)
                .setIssuer(issuer)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(expirationPeriod)))
                .addClaims(claims)
                .signWith(key)
                .compact();
    }
}
