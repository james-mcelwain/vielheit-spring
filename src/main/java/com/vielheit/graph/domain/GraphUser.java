package com.vielheit.graph.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@NodeEntity
public class GraphUser {
    @GraphId
    private Long id;

    private Long userId;

    @Relationship(type = Rel.OWNS)
    private List<Entry> entries;

    @JsonIgnore
    @Relationship(type = Rel.OWNS)
    private List<AbstractionType> abstractionTypes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }

    public List<AbstractionType> getAbstractionTypes() {
        return abstractionTypes;
    }

    public void setAbstractionTypes(List<AbstractionType> abstractionTypes) {
        this.abstractionTypes = abstractionTypes;
    }
}
