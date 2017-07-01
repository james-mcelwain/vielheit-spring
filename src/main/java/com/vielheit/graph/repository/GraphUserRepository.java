package com.vielheit.graph.repository;

import com.vielheit.graph.domain.GraphUser;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GraphUserRepository extends GraphRepository<GraphUser> {
    GraphUser findByUserId(Long userId);
}
