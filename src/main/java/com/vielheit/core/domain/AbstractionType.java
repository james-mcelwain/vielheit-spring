package com.vielheit.core.domain;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "AbstractionType", schema = "vielheit")
public class AbstractionType implements Serializable {
    @Embeddable
    public static class Id implements Serializable {
        private Id() {
        }

        @Column(name = "userId")
        private Long userId;

        @Column(name = "type")
        private String type;

        public Id(Long userId, String type) {
            this.userId = userId;
            this.type = type;
        }

        public Id(Long userId) {
            this.userId = userId;
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
    }

    @EmbeddedId
    private Id id;

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
}
