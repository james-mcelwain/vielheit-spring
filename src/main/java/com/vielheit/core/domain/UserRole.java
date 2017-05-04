package com.vielheit.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "UserRole", schema = "vielheit")
public class UserRole extends BaseEntity implements  Serializable {
    @Embeddable
    public static class Id implements Serializable {
        private static final long serialVersionUID = 1322120000551624359L;

        @Column(name = "userId")
        protected Long userId;

        @Enumerated(EnumType.STRING)
        @Column(name = "role")
        protected Role role;

        private Id() { }

        public Id(Long userId, Role role) {
            this.userId = userId;
            this.role = role;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;

            Id id = (Id) o;

            if (!userId.equals(id.userId))
                return false;

            return role == id.role;
        }

        @Override
        public int hashCode() {
            int result = userId.hashCode();
            result = 31 * result + role.hashCode();
            return result;
        }
    }

    @EmbeddedId
    @JsonIgnore
    Id id = new Id();

    @Enumerated(EnumType.STRING)
    @Column(name = "role", insertable=false, updatable=false)
    protected Role role;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }
}
