package cc.kojeve.vielheit.repository

import cc.kojeve.vielheit.domain.Role
import cc.kojeve.vielheit.domain.User
import org.springframework.data.repository.CrudRepository
import javax.transaction.Transactional

interface UserRepository : CrudRepository<User, Long> {
    fun <T> findByUsername(username: String, type: Class<T>): T

    interface AuthProjection {
        fun getUsername(): String
        fun getPassword(): String
        fun getRoles(): Set<Role>
    }
}

inline fun <reified T> UserRepository.findByUsername(username: String): T? {
    return findByUsername(username, T::class.java)
}