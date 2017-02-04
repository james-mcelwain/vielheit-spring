package com.vielheit.security.auth;

import java.util.Collection;

import com.vielheit.security.model.UserContext;
import com.vielheit.security.model.token.RawAccessJwtToken;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {
    private static final long serialVersionUID = 2877954820905567501L;

    private transient RawAccessJwtToken rawAccessToken;
    private transient  UserContext userContext;

    public JwtAuthenticationToken(RawAccessJwtToken unsafeToken) {
        super(null);
        this.rawAccessToken = unsafeToken;
        this.setAuthenticated(false);
    }

    public JwtAuthenticationToken(UserContext userContext, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.eraseCredentials();
        this.userContext = userContext;
        super.setAuthenticated(true);
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        if (authenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }
        super.setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return rawAccessToken;
    }

    @Override
    public Object getPrincipal() {
        return this.userContext;
    }

    @Override
    public void eraseCredentials() {        
        super.eraseCredentials();
        this.rawAccessToken = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;

        JwtAuthenticationToken that = (JwtAuthenticationToken) o;

        if (rawAccessToken != null ? !rawAccessToken.equals(that.rawAccessToken) : that.rawAccessToken != null)
            return false;
        return userContext != null ? userContext.equals(that.userContext) : that.userContext == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (rawAccessToken != null ? rawAccessToken.hashCode() : 0);
        result = 31 * result + (userContext != null ? userContext.hashCode() : 0);
        return result;
    }
}
