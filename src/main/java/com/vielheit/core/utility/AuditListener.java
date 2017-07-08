package com.vielheit.core.utility;

import com.vielheit.core.domain.BaseEntity;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

/**
 * jcm - 5/4/17.
 */
public class AuditListener {
    @PrePersist
    public void setCreate(BaseEntity baseEntity) {
        baseEntity.setCreateDate(LocalDateTime.now());
    }

    @PreUpdate
    public void setLastUpdate(BaseEntity baseEntity) {
       baseEntity.setUpdateDate(LocalDateTime.now());
    }
}
