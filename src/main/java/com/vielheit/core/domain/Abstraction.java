package com.vielheit.core.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * jcm - 4/15/17.
 */
@Entity
@Table(name = "Abstraction", schema = "vielheit")
public class Abstraction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    private AbstractionType abstractionType;

    @ManyToOne
    private User user;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AbstractionType getAbstractionType() {
        return abstractionType;
    }

    public void setAbstractionType(AbstractionType abstractionType) {
        this.abstractionType = abstractionType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

