package org.vielheit.core.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.UserDetailsManager
import org.vielheit.core.repository.UserRepository
import org.vielheit.core.repository.UserRoleRepository
import org.vielheit.core.security.JpaAuthenticationManager
import org.vielheit.core.security.JpaUserDetailsManager
import org.vielheit.core.security.JpaUserDetailsService

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    @Bean
    fun authenticationManager(userRepository: UserRepository, passwordEncoder: PasswordEncoder) = JpaAuthenticationManager(userRepository, passwordEncoder)

    @Bean
    fun userDetailsService(userRepository: UserRepository) = JpaUserDetailsService(userRepository)

    @Bean
    fun userDetailsManager(
            userRepository: UserRepository,
            userRoleRepository: UserRoleRepository,
            userDetailsService: UserDetailsService,
            authenticationManager: AuthenticationManager
    ) = JpaUserDetailsManager(userRepository, userRoleRepository, userDetailsService, authenticationManager)
}