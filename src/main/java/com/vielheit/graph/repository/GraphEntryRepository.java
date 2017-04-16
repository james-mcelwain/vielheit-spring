package com.vielheit.graph.repository;

import com.vielheit.graph.domain.GraphEntry;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GraphEntryRepository extends GraphRepository<GraphEntry> {

    @Query("MATCH (user:GraphUser {userId: {userId}})-[:OWNS]->(entries:GraphEntry) RETURN entries")
    List<GraphEntry> getEntriesByUserId(@Param("userId") Long userId);
}
