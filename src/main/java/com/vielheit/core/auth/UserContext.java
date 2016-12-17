package com.vielheit.core.auth;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;

public class UserContext {
    private final String email;
    private final GrantedAuthority grantedAuthority;

    private UserContext(String email, GrantedAuthority grantedAuthority) {
        this.email = email;
        this.grantedAuthority = grantedAuthority;
    }

    public static UserContext create(String username, GrantedAuthority grantedAuthority) {
        if (StringUtils.isBlank(username)) throw new IllegalArgumentException("Username is blank: " + username);
        return new UserContext(username, grantedAuthority);
    }

    public String getEmail() {
        return email;
    }

    public GrantedAuthority getAuthority() {
        return grantedAuthority;
    }
}
