package com.vielheit.app.domain;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@NodeEntity
public class Entry {
    @GraphId
    private Long id;

    @Relationship(type = "OWNS", direction = Relationship.INCOMING)
    private Long userId;

    @Relationship(type = "RELATES_TO", direction = Relationship.OUTGOING)
    private List<Entry> entries;

    @Relationship(type = "RELATES_TO", direction = Relationship.OUTGOING)
    private List<Space> spaces;

    @Relationship(type = "RELATES_TO", direction = Relationship.OUTGOING)
    private List<Time> times;

    @Relationship(type = "RELATES_TO", direction = Relationship.OUTGOING)
    private List<Concept> concepts;

    private String title;

    private String body;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
