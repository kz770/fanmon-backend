package com.example.fanmon_be.global.security.jwt;

import com.example.fanmon_be.global.security.UserPrincipal;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final UserPrincipal principal;

    public JwtAuthenticationToken(UserPrincipal principal, WebAuthenticationDetails details) {
        super(principal.getAuthorities());
        this.principal = principal;
        setAuthenticated(true);
        super.setDetails(details);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public UserPrincipal getPrincipal() {
        return principal;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }
}
