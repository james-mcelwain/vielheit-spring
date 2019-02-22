package cc.kojeve.vielheit.service

import cc.kojeve.vielheit.config.security.JwtTokenProvider
import cc.kojeve.vielheit.domain.Role
import cc.kojeve.vielheit.domain.User
import cc.kojeve.vielheit.request.RegistrationRequest
import cc.kojeve.vielheit.dto.UserData
import cc.kojeve.vielheit.repository.UserRepository
import cc.kojeve.vielheit.repository.findByUsername
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken

@Component
class UserService(
        override val repository: UserRepository,
        val passwordEncoder: PasswordEncoder,
        val authenticationManager: AuthenticationManager,
        val jwtTokenProvider: JwtTokenProvider
) : Service<User, UserData, RegistrationRequest>() {

    override fun save(registrationRequest: RegistrationRequest): UserData {
        val user = User(
                username = registrationRequest.username,
                password = passwordEncoder.encode(registrationRequest.password),
                roles = listOf(Role.ADMIN)
        )

        return UserData(repository.save(user))
    }

    fun findById(id: Long): UserData? {
        return repository.findById(id)
                .map { UserData(it) }
                .orElse(null)
    }

    fun auth(username: String, password: String): String {
        authenticationManager.authenticate(UsernamePasswordAuthenticationToken(username, password))
        return jwtTokenProvider.createToken(username, repository.findByUsername<User>(username)?.roles!!)
    }

    fun findByUsername(username: String): UserData? {
        return repository.findByUsername<User>(username)?.let {
            return UserData(it)
        }
    }
}