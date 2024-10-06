package com.example.fanmon_be.global.security.jwt;

import com.example.fanmon_be.global.security.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Pattern BEARER_PATTERN = Pattern.compile("^Bearer (.+?)$");
    private final JwtPlugin jwtPlugin;

    public JwtAuthenticationFilter(JwtPlugin jwtPlugin) {
        this.jwtPlugin = jwtPlugin;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, IOException {
        String jwt = getBearerToken(request);
        if (jwt != null) {
            try {
            Jws<Claims> claimsJws = jwtPlugin.validateToken(jwt);
            Claims claims = claimsJws.getBody();

            UUID userId = UUID.fromString(claims.getSubject()); //uuid 문자열로 가져온뒤 uuid 로 변환
            String role = claims.get("role", String.class);
            String email = claims.get("email", String.class);
            UserPrincipal principal = new UserPrincipal(
                    userId,
                    email,
                    Set.of(role)
            );

            WebAuthenticationDetailsSource detailsSource = new WebAuthenticationDetailsSource();
            WebAuthenticationDetails details = detailsSource.buildDetails(request);

            JwtAuthenticationToken authentication = new JwtAuthenticationToken(principal, details);


            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
    }
    filterChain.doFilter(request, response);
}

    private String getBearerToken(HttpServletRequest request) {
        String headerValue = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (headerValue == null) {
            return null;
        }
        var matcher = BEARER_PATTERN.matcher(headerValue);
        if (matcher.find()) {
            return matcher.group(1); //Bearer 뒤 토큰만 추출
        }
        return null;
    }
}