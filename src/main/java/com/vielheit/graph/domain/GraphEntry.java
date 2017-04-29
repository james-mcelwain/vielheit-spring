package com.vielheit.graph.domain;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Set;

@NodeEntity
public class GraphEntry {
    @GraphId
    private Long id;

    private Long entryId;

    @Relationship(type = Rel.OWNS, direction = Relationship.INCOMING)
    private GraphUser user;

    @Relationship(type = Rel.RELATES_TO, direction = Relationship.OUTGOING)
    private Set<GraphEntry> entries;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEntryId() {
        return entryId;
    }

    public void setEntryId(Long entryId) {
        this.entryId = entryId;
    }

    public GraphUser getUser() {
        return user;
    }

    public void setUser(GraphUser user) {
        this.user = user;
    }

    public Set<GraphEntry> getEntries() {
        return entries;
    }

    public void setEntries(Set<GraphEntry> entries) {
        this.entries = entries;
    }
}
