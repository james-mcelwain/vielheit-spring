package com.vielheit.core.domain;

import com.vielheit.security.auth.JwtAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.Column;

/**
 * jcm - 5/3/17.
 */
public abstract class BaseEntity<T> {

    @Column(name = "deleted")
    private boolean deleted = false;

    @Column(name = "createUser")
    private Long createUser = userId();

    public Long userId() {
        JwtAuthenticationToken jwt = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return jwt.getUserContext().getUserId();
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }
}
