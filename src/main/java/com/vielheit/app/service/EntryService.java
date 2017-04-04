package com.vielheit.app.service;

import com.vielheit.app.domain.Entry;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public interface EntryService {
    Optional<Entry> saveEntry(Entry entry);
}
