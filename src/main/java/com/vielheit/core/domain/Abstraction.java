package com.vielheit.core.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * jcm - 4/15/17.
 */
@Entity
public class Abstraction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "userId", insertable = false, updatable = false),
            @JoinColumn(name = "type", insertable = false, updatable = false)
    })
    private AbstractionType abstractionType;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User user;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany
    @JoinColumn(name = "id")
    private List<Entry> entries;

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

    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }
}

