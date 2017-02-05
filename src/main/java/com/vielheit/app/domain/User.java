package com.vielheit.app.domain;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@NodeEntity
public class User {
    @GraphId
    private Long id;

    @Relationship(type="OWNS", direction=Relationship.OUTGOING)
    private List<Entry> entries;

    @Relationship(type="OWNS", direction=Relationship.OUTGOING)
    private List<Space> spaces;

    @Relationship(type="OWNS", direction=Relationship.OUTGOING)
    private List<Time> times;

    @Relationship(type="OWNS", direction=Relationship.OUTGOING)
    private List<Concept> concepts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }

    public List<Space> getSpaces() {
        return spaces;
    }

    public void setSpaces(List<Space> spaces) {
        this.spaces = spaces;
    }

    public List<Time> getTimes() {
        return times;
    }

    public void setTimes(List<Time> times) {
        this.times = times;
    }

    public List<Concept> getConcepts() {
        return concepts;
    }

    public void setConcepts(List<Concept> concepts) {
        this.concepts = concepts;
    }
}
