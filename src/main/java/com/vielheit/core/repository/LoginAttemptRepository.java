package com.vielheit.core.repository;

import com.vielheit.core.domain.LoginAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * jcm - 5/4/17.
 */
public interface LoginAttemptRepository extends JpaRepository<LoginAttempt, Long> {

    @Query("SELECT l FROM LoginAttempt l WHERE l.loginAttemptDate BETWEEN :from AND :to AND l.emailAddress = :emailAddress")
    List<LoginAttempt> getRecentLoginAttempts(
            @Param("emailAddress") String emailAddress,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to
    );
}
