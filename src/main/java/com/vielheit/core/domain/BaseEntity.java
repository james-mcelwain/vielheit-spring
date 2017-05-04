package com.vielheit.core.domain;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;

/**
 * jcm - 5/3/17.
 */
@MappedSuperclass
public abstract class BaseEntity {

    @Column(name = "deleted", columnDefinition = "boolean default false", nullable = false)
    private boolean deleted = false;

    @CreationTimestamp
    @Column(name = "createDate")
    private Timestamp createDate;

    @UpdateTimestamp
    @Column(name = "updateDate")
    private Timestamp updateDate;

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }
}
