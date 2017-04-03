package com.vielheit.app.domain;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

/**
 * Non-precise time condition -- I.e., "last summer," "childhood".
 * Entries have a create / update date
 */
@NodeEntity
public class Time {
    @GraphId
    Long id;

    @Relationship(type = "OWNS", direction = Relationship.INCOMING)
    Long userId;

    String name;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
