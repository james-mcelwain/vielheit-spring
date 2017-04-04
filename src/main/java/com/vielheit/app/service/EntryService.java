package com.vielheit.app.service;

import com.vielheit.app.domain.Entry;
import com.vielheit.core.service.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public interface EntryService extends Service {
    Optional<Entry> saveEntry(Entry entry);

    Optional<List<Entry>> getEntries(Long userId);
}
