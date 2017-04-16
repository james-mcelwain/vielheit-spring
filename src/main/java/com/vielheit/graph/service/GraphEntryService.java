package com.vielheit.graph.service;

import com.vielheit.graph.domain.GraphEntry;
import com.vielheit.core.service.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public interface GraphEntryService extends Service {
    Optional<GraphEntry> saveEntry(GraphEntry graphEntry);

    Optional<List<GraphEntry>> getEntries(Long userId);
}
