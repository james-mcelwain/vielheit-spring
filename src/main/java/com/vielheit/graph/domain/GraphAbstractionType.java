package com.vielheit.graph.domain;

import com.vielheit.core.domain.AbstractionType;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class GraphAbstractionType {
    @GraphId
    private Long id;

    private AbstractionType.Id abstractionTypeId;

    @Relationship(type = Rel.OWNS, direction = Relationship.INCOMING)
    private GraphUser graphUser;

    private String type;

    private String descrtiption;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AbstractionType.Id getAbstractionTypeId() {
        return abstractionTypeId;
    }

    public void setAbstractionTypeId(AbstractionType.Id abstractionTypeId) {
        this.abstractionTypeId = abstractionTypeId;
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

    public String getDescrtiption() {
        return descrtiption;
    }

    public void setDescrtiption(String descrtiption) {
        this.descrtiption = descrtiption;
    }
}
