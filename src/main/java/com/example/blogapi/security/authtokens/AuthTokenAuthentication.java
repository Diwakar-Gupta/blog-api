package com.example.blogapi.security.authtokens;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.security.auth.Subject;
import java.util.Collection;

public class AuthTokenAuthentication implements Authentication {
    private String token;
    private Integer userId;

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public AuthTokenAuthentication(String token) {
        this.token = token;
        userId = null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getCredentials() {
        return token;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Integer getPrincipal() {
        return userId;
    }

    @Override
    public boolean isAuthenticated() {
        return userId != null;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean implies(Subject subject) {
        return Authentication.super.implies(subject);
    }
}
