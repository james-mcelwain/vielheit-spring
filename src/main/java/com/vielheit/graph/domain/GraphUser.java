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
    private List<GraphEntry> entries;

    @JsonIgnore
    @Relationship(type = Rel.OWNS)
    private List<GraphAbstractionType> graphAbstractionTypes;

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

    public List<GraphEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<GraphEntry> entries) {
        this.entries = entries;
    }

    public List<GraphAbstractionType> getGraphAbstractionTypes() {
        return graphAbstractionTypes;
    }

    public void setGraphAbstractionTypes(List<GraphAbstractionType> graphAbstractionTypes) {
        this.graphAbstractionTypes = graphAbstractionTypes;
    }
}
