package com.vielheit.core.domain;

import com.vielheit.core.utility.AuditListener;
import com.vielheit.core.utility.LocalDateTimeConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * jcm - 5/3/17.
 */
@MappedSuperclass
@EntityListeners({AuditListener.class})
public abstract class BaseEntity {

    @Column(name = "deleted", columnDefinition = "boolean default false", nullable = false)
    private boolean deleted = false;

    @Column(name = "createDate")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime createDate;

    @Column(name = "updateDate")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime updateDate;

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }
}
