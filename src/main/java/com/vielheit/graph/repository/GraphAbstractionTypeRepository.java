package com.vielheit.graph.repository;


import com.vielheit.core.domain.AbstractionType;
import com.vielheit.graph.domain.GraphAbstractionType;
import com.vielheit.graph.domain.GraphUser;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface GraphAbstractionTypeRepository extends GraphRepository<GraphAbstractionType> {
    List<GraphAbstractionType> findByGraphUser(GraphUser graphUser);

    @Query("MATCH (user:GraphUser {userId: {userId}})-[:OWNS]->(gat:GraphAbstractionType {type: {type}}) RETURN gat")
    GraphAbstractionType findTypeByNameAndUserId(@Param("userId") Long userId, @Param("type") String type);

    @Query("MATCH (gat:GraphAbstractionType {userId: {userId}}) RETURN gat")
    List<GraphAbstractionType> findByUserId(@Param("userId") Long userId);
}
