package cc.kojeve.vielheit.service

import cc.kojeve.vielheit.domain.Role
import cc.kojeve.vielheit.domain.User
import cc.kojeve.vielheit.dto.RegistrationData
import cc.kojeve.vielheit.dto.UserData
import cc.kojeve.vielheit.repository.UserRepository
import cc.kojeve.vielheit.repository.findByUsername
import cc.kojeve.vielheit.util.VielheitException
import org.springframework.stereotype.Component

@Component
class UserService(override val repository: UserRepository): Service<User>() {
    fun save(registrationData: RegistrationData): UserData {
        val user = User(
                username = registrationData.username,
                password = registrationData.password,
                roles = listOf(Role.ADMIN)
        )

        return UserData(repository.save(user))
    }


    fun findById(id: Long): UserData? {
        return repository.findById(id)
                .map { UserData(it) }
                .orElse(null)
    }



    fun findByUsername(username: String): UserData? {
        return repository.findByUsername<User>(username)?.let {
            return UserData(it)
        }
    }
}