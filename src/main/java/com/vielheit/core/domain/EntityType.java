package com.vielheit.core.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "EntityType", schema = "vielheit")
public class EntityType implements Serializable {
    @Embeddable
    public static class Id implements Serializable {
        private Id() {}

        @Column(name = "userId")

        private Long userId;

        @Column(name = "type")
        private String type;

        public Id(Long userId, String type) {
            this.userId = userId;
            this.type = type;
        }

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Id id = (Id) o;

            if (!userId.equals(id.userId)) return false;
            return type.equals(id.type);
        }

        @Override
        public int hashCode() {
            int result = userId.hashCode();
            result = 31 * result + type.hashCode();
            return result;
        }
    }

    @EmbeddedId
    Id id = new Id();

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id")
    private User user;

    @Column(name = "description")
    private String description;

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntityType that = (EntityType) o;

        if (!id.equals(that.id)) return false;
        if (!user.equals(that.user)) return false;
        return description.equals(that.description);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + user.hashCode();
        result = 31 * result + description.hashCode();
        return result;
    }
}
