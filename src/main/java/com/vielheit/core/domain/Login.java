package com.vielheit.core.domain;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * jcm - 5/3/17.
 */
@Entity
@Table(name = "\"Login\"", schema = "vielheit")
public class Login extends BaseEntity<Login> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "userId")
    private Long userId;
}
