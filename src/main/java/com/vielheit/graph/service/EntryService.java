package com.vielheit.graph.service;

import com.vielheit.graph.domain.Entry;
import com.vielheit.core.service.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public interface EntryService extends Service {
    Optional<Entry> saveEntry(Entry entry);

    Optional<List<Entry>> getEntries(Long userId);
}
