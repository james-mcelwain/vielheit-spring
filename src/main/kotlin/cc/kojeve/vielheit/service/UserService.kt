package cc.kojeve.vielheit.service

import cc.kojeve.vielheit.domain.User
import cc.kojeve.vielheit.util.VielheitException
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component

@Component
class UserService(override val repository: CrudRepository<User, Long>): Service<User>() {

    fun save(user: User): User {
        user.id?.let {
            if (repository.existsById(it)) {
                throw VielheitException("User already exists")
            }
        }

        return repository.save(user)
    }


    fun findById(id: Long): User? {
        return repository.findById(id).orElse(null)
    }
}