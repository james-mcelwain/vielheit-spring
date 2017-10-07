package com.vielheit.graph.repository;

import com.vielheit.graph.domain.Abstraction;
import com.vielheit.graph.domain.GraphUser;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import java.util.Set;

/**
 * jcm - 4/15/17.
 */
public interface AbstractionRepository extends GraphRepository<Abstraction> {
    @Query("MATCH (user:GraphUser {userId: {userId}})-[:OWNS]->(abstractions:Abstraction) RETURN abstractions")
    Set<Abstraction> findByUserId(@Param("userId") Long userId);
}
