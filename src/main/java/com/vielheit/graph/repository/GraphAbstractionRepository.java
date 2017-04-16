package com.vielheit.graph.repository;

import com.vielheit.graph.domain.GraphAbstraction;
import com.vielheit.graph.domain.GraphAbstractionType;
import com.vielheit.graph.domain.GraphUser;
import org.springframework.data.neo4j.repository.GraphRepository;

import java.util.List;

/**
 * jcm - 4/15/17.
 */
public interface GraphAbstractionRepository extends GraphRepository<GraphAbstraction> {
    List<GraphAbstraction> findByUser(GraphUser graphUser);
}
