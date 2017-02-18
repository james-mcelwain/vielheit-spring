package com.vielheit.security.model;

import java.util.List;

import com.vielheit.core.domain.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;

public class UserContext {
    private User user;
    private final List<GrantedAuthority> authorities;

    private UserContext(User user, List<GrantedAuthority> authorities) {
        this.user = user;
        this.authorities = authorities;
    }
    
    public static UserContext create(User user, List<GrantedAuthority> authorities) {
        if (user == null) throw new IllegalArgumentException();
        return new UserContext(user, authorities);
    }

    public User getUser() {
        return user;
    }

    public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }
}
