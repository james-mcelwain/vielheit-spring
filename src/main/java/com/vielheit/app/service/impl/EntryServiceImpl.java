package com.vielheit.app.service.impl;

import com.vielheit.app.domain.Entry;
import com.vielheit.app.repository.EntryRepository;
import com.vielheit.app.service.EntryService;
import com.vielheit.core.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EntryServiceImpl implements EntryService, Service {
    @Autowired
    private EntryRepository entryRepository;

    @Override
    public Optional<Entry> saveEntry(Entry entry) {
        return Optional.ofNullable(entryRepository.save(entry));
    }
}
