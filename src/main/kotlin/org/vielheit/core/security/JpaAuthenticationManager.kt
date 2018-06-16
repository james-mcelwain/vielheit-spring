package org.vielheit.core.security

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.crypto.password.PasswordEncoder
import org.vielheit.core.repository.UserRepository

class JpaAuthenticationManager(val userRepository: UserRepository, val passwordEncoder: PasswordEncoder) : AuthenticationManager {
    override fun authenticate(authentication: Authentication?): Authentication {
        val email = authentication!!.principal.toString()
        val password = authentication.credentials.toString()

        val user = userRepository.findByEmail(email) ?: throw BadCredentialsException(email)

        if (!user.active) throw DisabledException(email)

        if (!passwordEncoder.matches(password, user.password)) throw BadCredentialsException(email)

        return UsernamePasswordAuthenticationToken(email, password, user.roles.map { SimpleGrantedAuthority(it.role.name) }.toSet())
    }
}