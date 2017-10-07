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
    AbstractionType findTypeByNameAndUserId(@Param("userId") Long userId, @Param("type") String type);

    @Query("MATCH (at:AbstractionType {userId: {userId}}) RETURN at")
    Set<AbstractionType> findByUserId(@Param("userId") Long userId);
}
