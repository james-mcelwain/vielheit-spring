package cc.kojeve.vielheit.config.security

import cc.kojeve.vielheit.repository.UserRepository
import cc.kojeve.vielheit.repository.findByUsername
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component

class DefaultUserDetailService(val userRepository: UserRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        val username = username ?: throw UsernameNotFoundException(username)
        val u = userRepository.findByUsername<UserRepository.AuthProjection>(username) ?: throw UsernameNotFoundException(username)

        return User.builder()
                .username(username)
                .password(u.getPassword())
                .authorities(u.getRoles())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build()
    }
}