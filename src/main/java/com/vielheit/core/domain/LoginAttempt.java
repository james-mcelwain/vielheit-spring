package com.vielheit.core.domain;

import com.vielheit.core.utility.LocalDateTimeConverter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * jcm - 5/4/17.
 */
@Entity
@Table(name = "\"LoginAttempt\"", schema = "vielheit")
public class LoginAttempt extends BaseEntity {
    public enum FailureReason {
        EMAIL("INVALID_EMAIL"),
        PASSWORD("INVALID_PASSWORD"),
        UNKNOWN("UNKOWN");

        private String failureReason;

        private FailureReason(String failureReason) {
            this.failureReason = failureReason;
        }

        @Override
        public String toString() {
            return this.failureReason;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "inetAddress")
    private String inetAddress;

    @Column(name = "emailAddress")
    private String emailAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "failureReason")
    private FailureReason failureReason;

    @Column(name = "loginAttemptDate")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime loginAttemptDate = LocalDateTime.now();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInetAddress() {
        return inetAddress;
    }

    public void setInetAddress(String inetAddress) {
        this.inetAddress = inetAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public FailureReason getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(FailureReason failureReason) {
        this.failureReason = failureReason;
    }

    public LocalDateTime getLoginAttemptDate() {
        return loginAttemptDate;
    }

    public void setLoginAttemptDate(LocalDateTime loginAttemptDate) {
        this.loginAttemptDate = loginAttemptDate;
    }
}
