package com.vielheit.graph.repository;


import com.vielheit.graph.domain.AbstractionType;
import com.vielheit.graph.domain.GraphUser;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AbstractionTypeRepository extends GraphRepository<AbstractionType> {
    List<AbstractionType> findByGraphUser(GraphUser graphUser);

    @Query("MATCH (user:GraphUser {userId: {userId}})-[:OWNS]->(at:AbstractionType {type: {type}}) RETURN at")
    AbstractionType findTypeByNameAndUserId(@Param("userId") Long userId, @Param("type") String type);

    @Query("MATCH (at:AbstractionType {userId: {userId}}) RETURN at")
    List<AbstractionType> findByUserId(@Param("userId") Long userId);
}
