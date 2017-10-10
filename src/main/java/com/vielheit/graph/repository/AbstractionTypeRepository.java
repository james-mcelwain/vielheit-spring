package com.vielheit.graph.repository;


import com.vielheit.graph.domain.AbstractionType;
import com.vielheit.graph.domain.GraphUser;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface AbstractionTypeRepository extends GraphRepository<AbstractionType> {
    @Query("MATCH (user:GraphUser {userId: {userId}})-[:OWNS]->(at:AbstractionType {type: {type}}) RETURN at")
    AbstractionType findTypeByTypeAndUserId(@Param("userId") Long userId, @Param("type") String type);

    @Query("MATCH (user:GraphUser {userId: {userId}})-[:OWNS]->(at:AbstractionType) RETURN at")
    Set<AbstractionType> findByUserId(@Param("userId") Long userId);
}
