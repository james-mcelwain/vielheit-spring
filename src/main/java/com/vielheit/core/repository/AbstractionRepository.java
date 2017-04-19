package com.vielheit.core.repository;

import com.vielheit.core.domain.Abstraction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * jcm - 4/16/17.
 */
public interface AbstractionRepository extends JpaRepository<Abstraction, Long> {
    List<Abstraction> findByNameAndUserId(String name, Long userId);
}
