package com.vielheit.graph.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@NodeEntity
public class Abstraction {
    @GraphId
    private Long id;

    private String name;

    private String description;

    @JsonIgnore
    @Relationship(type = Rel.OWNS, direction = Relationship.INCOMING)
    private GraphUser user;

    @Relationship(type = Rel.TYPE_OF, direction = Relationship.INCOMING)
    private AbstractionType abstractionType;

    @Relationship(type = Rel.RELATES_TO)
    private List<Entry> entryList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public GraphUser getUser() {
        return user;
    }

    public void setUser(GraphUser user) {
        this.user = user;
    }

    public AbstractionType getAbstractionType() {
        return abstractionType;
    }

    public void setAbstractionType(AbstractionType abstractionType) {
        this.abstractionType = abstractionType;
    }

    public List<Entry> getEntryList() {
        return entryList;
    }

    public void setEntryList(List<Entry> entryList) {
        this.entryList = entryList;
    }
}
