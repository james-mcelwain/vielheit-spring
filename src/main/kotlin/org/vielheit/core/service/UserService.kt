package org.vielheit.core.service

import org.springframework.stereotype.Component
import org.vielheit.core.domain.User
import org.vielheit.core.repository.UserNodeRepository
import org.vielheit.core.repository.UserRepository
import javax.transaction.Transactional

@Component
@Transactional
class UserService(val userRepository: UserRepository, val userNodeRepository: UserNodeRepository) {
    fun createUser(user: User) {


        userRepository.save(user)
    }
}