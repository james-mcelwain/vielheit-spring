package com.vielheit.graph.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class AbstractionType {
    @GraphId
    private Long id;

    @JsonIgnore
    @Relationship(type = Rel.OWNS, direction = Relationship.INCOMING)
    private GraphUser graphUser;

    private String type;

    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GraphUser getUser() {
        return graphUser;
    }

    public void setUser(GraphUser graphUser) {
        this.graphUser = graphUser;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
