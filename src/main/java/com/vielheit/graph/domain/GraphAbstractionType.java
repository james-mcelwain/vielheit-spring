package com.vielheit.graph.domain;

import com.sun.corba.se.impl.orbutil.graph.Graph;
import com.vielheit.core.domain.AbstractionType;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class GraphAbstractionType {
    @GraphId
    private Long id;

    @Relationship(type = Rel.OWNS, direction = Relationship.INCOMING)
    private GraphUser user;

    private String type;

    private String descrtiption;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GraphUser getUser() {
        return user;
    }

    public void setUser(GraphUser user) {
        this.user = user;
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
