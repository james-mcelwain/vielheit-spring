package com.vielheit.core.service;

import com.vielheit.core.domain.Entry;

import java.util.List;

/**
 * jcm - 4/30/17.
 */
public interface EntryService extends Service {
    List<Entry> findEntries(Long userId);
}
