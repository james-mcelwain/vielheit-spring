package com.vielheit.core.security.model;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;

public class UserContext {
    private final String emailAddress;
    private final List<GrantedAuthority> authorities;

    private UserContext(String emailAddress, List<GrantedAuthority> authorities) {
        this.emailAddress = emailAddress;
        this.authorities = authorities;
    }
    
    public static UserContext create(String emailAddress, List<GrantedAuthority> authorities) {
        if (StringUtils.isBlank(emailAddress)) throw new IllegalArgumentException("EmailAddress is blank: " + emailAddress);
        return new UserContext(emailAddress, authorities);
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }
}
