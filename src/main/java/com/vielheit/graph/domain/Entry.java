package com.vielheit.app.domain;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Set;

@NodeEntity
public class Entry {
    @GraphId
    private Long id;

    @Relationship(type = Rel.OWNS, direction = Relationship.INCOMING)
    private GraphUser user;

    @Relationship(type = Rel.RELATES_TO, direction = Relationship.OUTGOING)
    private Set<Entry> entries;
}
