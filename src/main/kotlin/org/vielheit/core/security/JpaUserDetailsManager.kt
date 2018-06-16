package org.vielheit.core.security

import org.springframework.security.access.AccessDeniedException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.provisioning.UserDetailsManager
import org.vielheit.core.domain.User
import org.vielheit.core.domain.UserRole
import org.vielheit.core.domain.UserRoleType
import org.vielheit.core.repository.UserRepository
import org.vielheit.core.repository.UserRoleRepository
import org.vielheit.core.util.logger

class JpaUserDetailsManager(
        val userRepository: UserRepository,
        val userRoleRepository: UserRoleRepository,
        val userDetailsService: UserDetailsService,
        val authenticationManager: AuthenticationManager
) : UserDetailsManager {
    val log by logger()

    override fun userExists(email : String?): Boolean {
        return userRepository.countByEmail(email!!) > 0
    }

    override fun updateUser(user: UserDetails?) {
        val foundUser = userRepository.findByEmail(user!!.username) ?: throw UsernameNotFoundException(user.username)
        user.authorities.forEach {
            if (foundUser.roles.none { r -> r.role.name.equals(it.authority) }) {
                val newRole = userRoleRepository.save(UserRole(user = foundUser, role = UserRoleType.valueOf(it.authority)))
                foundUser.roles.plus(newRole)
            }
        }
        val updatedUser = foundUser.copy(password = user.password, active = user.isEnabled && user.isAccountNonLocked)

        userRepository.saveAndFlush(updatedUser)
    }

    override fun loadUserByUsername(email: String?): UserDetails {
        return userDetailsService.loadUserByUsername(email)
    }

    override fun createUser(user: UserDetails?) {
        val savedUser = userRepository.save(User(email = user!!.username, firstName = "MANAGED", lastName = "USER", password = user.password, roles = emptySet()))
        user.authorities.forEach {
            val role = userRoleRepository.save(UserRole(user = savedUser, role = UserRoleType.valueOf(it.authority)));
            savedUser.roles.plus(role)
        }


        userRepository.saveAndFlush(savedUser)
    }

    override fun deleteUser(email: String?) {
        val user = userRepository.findByEmail(email!!) ?: throw UsernameNotFoundException(email)
        userRepository.delete(user)
    }

    override fun changePassword(oldPassword: String?, newPassword: String?) {
        val currentUser = SecurityContextHolder.getContext().authentication
        if (currentUser == null) {
            throw AccessDeniedException("Can't change password as no Authentication object found in context for current user.")
        } else {
            val email= currentUser.name
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(email, oldPassword))

            log.debug("Changing password for user '$email'")

            val user = userRepository.findByEmail(email) ?: throw UsernameNotFoundException(email)
            userRepository.saveAndFlush(user.copy(password = newPassword!!))
            SecurityContextHolder.getContext().authentication = this.createNewAuthentication(currentUser, newPassword)
        }
    }

    private fun createNewAuthentication(currentAuth: Authentication, newPassword: String): Authentication {
        val user = loadUserByUsername(currentAuth.name)
        val newAuthentication = UsernamePasswordAuthenticationToken(user, null, user.authorities)
        newAuthentication.details = currentAuth.details
        return newAuthentication
    }
}