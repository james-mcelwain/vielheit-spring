package org.vielheit.core.domain

import javax.persistence.*

@Entity
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["userRoleId", "role"])])
data class UserRole(
        @Id
        @GeneratedValue
        @Column(name = "userRoleId")
        val id: Int? = null,

        @ManyToOne
        @JoinColumn(name = "userId")
        val user: User,

        val role: UserRoleType
)

enum class UserRoleType {
    ROLE_USER,
    ROLE_ADMIN
}