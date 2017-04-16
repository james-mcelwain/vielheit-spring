package com.vielheit.graph.service.impl;

import com.vielheit.graph.domain.GraphEntry;
import com.vielheit.graph.domain.GraphUser;
import com.vielheit.graph.repository.GraphEntryRepository;
import com.vielheit.graph.repository.GraphUserRepository;
import com.vielheit.graph.service.GraphEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class GraphEntryServiceImpl implements GraphEntryService {
    @Autowired
    private GraphEntryRepository entryRepository;
    @Autowired
    private GraphUserRepository graphUserRepository;

    @Override
    public Optional<GraphEntry> saveEntry(GraphEntry graphEntry) {
        GraphUser user = graphUserRepository.findByUserId(graphEntry.getUser().getUserId());
        graphEntry.setUser(user);

        return Optional.ofNullable(entryRepository.save(graphEntry));
    }

    @Override
    public Optional<List<GraphEntry>> getEntries(Long userId) {
        return any(() -> entryRepository.getEntriesByUserId(userId));
    }
}
