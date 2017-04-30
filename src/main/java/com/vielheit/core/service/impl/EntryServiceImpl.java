package com.vielheit.core.service.impl;

import com.vielheit.core.domain.Entry;
import com.vielheit.core.repository.EntryRepository;
import com.vielheit.core.service.EntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * jcm - 4/30/17.
 */
@Component
public class EntryServiceImpl implements EntryService{
    @Autowired
    EntryRepository entryRepository;

    @Override
    public List<Entry> findEntries(Long userId) {
        return entryRepository.findByUserId(userId);
    }
}
