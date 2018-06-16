package org.vielheit.core.security

import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.vielheit.core.repository.UserRepository

class JpaUserDetailsService(val userRepository: UserRepository) : UserDetailsService  {
    override fun loadUserByUsername(email: String?): UserDetails {
        if (email == null) {
            throw IllegalArgumentException("Email cannot be null")
        }

        val user = userRepository.findByEmail(email) ?: throw UsernameNotFoundException(email)

        return User.builder()
                .apply {
                    username(user.email)
                    password(user.password)
                    accountLocked(user.active)
                    accountExpired(user.active)
                    authorities(*user.roles.map { it.role.name }.toTypedArray())
                }.build()
    }
}