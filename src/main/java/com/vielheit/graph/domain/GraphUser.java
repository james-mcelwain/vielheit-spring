package com.vielheit.app.domain;

import com.vielheit.core.domain.User;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@NodeEntity
public class GraphUser {
    @GraphId
    private Long id;

    private Long userId;

    @Relationship(type = Rel.OWNS, direction = Relationship.OUTGOING)
    private List<Entry> entries;
}
