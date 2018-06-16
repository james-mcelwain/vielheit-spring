package org.vielheit.core.domain

import javax.persistence.*

@Entity
@Table(name = "\"User\"")
data class User(
        @Id
        @GeneratedValue
        @Column(name = "userId")
        val id: Int? = null,

        @Column(nullable = false)
        val active: Boolean = true,

        @Column(nullable = false)
        val firstName: String,

        @Column(nullable = false)
        val lastName: String,

        @Column(nullable = false, unique = true)
        val email: String,

        @Column(nullable = false)
        val password: String,

        @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
        @JoinColumn(name = "userId")
        val roles: Set<UserRole> = emptySet()
)