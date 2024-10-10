package com.example.fanmon_be.global.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class UserPrincipal {
    private final UUID id;
    private final String email;
    private final Collection<GrantedAuthority> authorities;

    public UserPrincipal(UUID id, String email, Set<String> roles) {
        this.id = id;
        this.email = email;
        this.authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }
}