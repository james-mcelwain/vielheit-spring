package com.vielheit.graph.service.impl;

import com.vielheit.graph.domain.Entry;
import com.vielheit.graph.domain.GraphUser;
import com.vielheit.graph.repository.EntryRepository;
import com.vielheit.graph.repository.GraphUserRepository;
import com.vielheit.graph.service.EntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class EntryServiceImpl implements EntryService {
    @Autowired
    private EntryRepository entryRepository;
    @Autowired
    private GraphUserRepository graphUserRepository;

    @Override
    public Optional<Entry> saveEntry(Entry entry) {
        GraphUser user = graphUserRepository.findByUserId(entry.getUser().getUserId());
        entry.setUser(user);

        return Optional.ofNullable(entryRepository.save(entry));
    }

    @Override
    public Optional<List<Entry>> getEntries(Long userId) {
        return any(() -> entryRepository.getEntriesByUserId(userId));
    }
}
