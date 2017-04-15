package com.vielheit.app.repository;

import com.vielheit.app.domain.GraphUser;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GraphUserRepository extends GraphRepository<GraphUser> {
    GraphUser findByUserId(Long userId);
}
