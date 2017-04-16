package com.vielheit.graph.repository;


import com.vielheit.graph.domain.GraphAbstractionType;
import com.vielheit.graph.domain.GraphUser;
import org.springframework.data.neo4j.repository.GraphRepository;

import java.util.List;

public interface GraphAbstractionTypeRepository extends GraphRepository<GraphAbstractionType> {
     List<GraphAbstractionType> findByGraphUser(GraphUser graphUser);
}
