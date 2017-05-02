package com.vielheit.graph.repository;

import com.vielheit.graph.domain.Abstraction;
import com.vielheit.graph.domain.GraphUser;
import org.springframework.data.neo4j.repository.GraphRepository;

import java.util.List;

/**
 * jcm - 4/15/17.
 */
public interface AbstractionRepository extends GraphRepository<Abstraction> {
    List<Abstraction> findByUser(GraphUser graphUser);
}
