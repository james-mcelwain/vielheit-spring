package com.vielheit.app.repository;

import com.vielheit.app.domain.Entry;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntryRepository extends GraphRepository<Entry> {

}
