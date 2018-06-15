package org.vielheit.core.domain

import org.neo4j.ogm.annotation.NodeEntity
import javax.persistence.*

@Entity
@Table(name = "\"User\"")
data class User(
        @Id
        @GeneratedValue
        val id: Int? = null,

        @Column(nullable = false)
        val active: Boolean = true,

        @Column(nullable = false)
        val firstName: String,

        @Column(nullable = false)
        val lastName: String,

        @Column(nullable = false, unique = true)
        val email: String
)