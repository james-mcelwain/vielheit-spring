package com.vielheit.security.model;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;

public class UserContext {
    private final Long userId;
    private final List<GrantedAuthority> authorities;

    private UserContext(Long userId, List<GrantedAuthority> authorities) {
        this.userId = userId;
        this.authorities = authorities;
    }
    
    public static UserContext create(Long userId, List<GrantedAuthority> authorities) {
        if (userId == null ) throw new IllegalArgumentException();
        return new UserContext(userId, authorities);
    }



    public Long getUserId() {
        return userId;
    }

    public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }
}
