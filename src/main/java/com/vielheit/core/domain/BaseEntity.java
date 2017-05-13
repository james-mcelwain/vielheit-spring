package com.vielheit.core.domain;

import com.vielheit.core.utility.Audit;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * jcm - 5/3/17.
 */
@MappedSuperclass
@EntityListeners({Audit.class})
public abstract class BaseEntity {

    @Column(name = "deleted", columnDefinition = "boolean default false", nullable = false)
    private boolean deleted = false;

    @Column(name = "createDate")
    private LocalDateTime createDate;

    @Column(name = "updateDate")
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
