package com.vielheit.core.domain;

import javax.persistence.*;

/**
 * jcm - 4/16/17.
 */
@Entity
@Table(name = "Entry", schema = "vielheit")
public class Entry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
}
