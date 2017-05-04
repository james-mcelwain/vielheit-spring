package com.vielheit.core.repository;

import com.vielheit.core.domain.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * jcm - 5/4/17.
 */
@Repository
public interface LoginRepository extends JpaRepository<Login, Long> {
}
