package org.vielheit.core.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.vielheit.core.domain.User
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, Int> {
    fun findByEmail(email: String): User?
}