package cc.kojeve.vielheit.repository

import cc.kojeve.vielheit.domain.User
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<User, Long> {
}