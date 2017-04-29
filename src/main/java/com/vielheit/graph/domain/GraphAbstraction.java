package com.vielheit.graph.domain;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@NodeEntity
public class GraphAbstraction{
    @GraphId
    private Long id;

    private Long abstractionId;

    private String name;

    private String description;

    @Relationship(type = Rel.OWNS, direction = Relationship.INCOMING)
    private GraphUser user;

    @Relationship(type = Rel.TYPE_OF, direction = Relationship.INCOMING)
    private GraphAbstractionType graphAbstractionType;

    @Relationship(type = Rel.RELATES_TO, direction = Relationship.OUTGOING)
    private List<GraphEntry> graphEntryList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAbstractionId() {
        return abstractionId;
    }

    public void setAbstractionId(Long abstractionId) {
        this.abstractionId = abstractionId;
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

    public GraphAbstractionType getGraphAbstractionType() {
        return graphAbstractionType;
    }

    public void setGraphAbstractionType(GraphAbstractionType graphAbstractionType) {
        this.graphAbstractionType = graphAbstractionType;
    }

    public List<GraphEntry> getGraphEntryList() {
        return graphEntryList;
    }

    public void setGraphEntryList(List<GraphEntry> graphEntryList) {
        this.graphEntryList = graphEntryList;
    }
}
