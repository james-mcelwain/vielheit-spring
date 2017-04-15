package com.vielheit.app.repository;

import com.vielheit.app.domain.Entry;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntryRepository extends GraphRepository<Entry> {

    @Query("MATCH (user:GraphUser {userId: {userId}})-[:OWNS]->(entries:Entry) RETURN entries")
    List<Entry> getEntriesByUserId(@Param("userId") Long userId);
}
