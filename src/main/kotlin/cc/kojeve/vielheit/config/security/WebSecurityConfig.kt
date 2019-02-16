package cc.kojeve.vielheit.config.security

import cc.kojeve.vielheit.repository.UserRepository
import cc.kojeve.vielheit.repository.findByUsername
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
@EnableWebSecurity
class WebSecurityConfig(val userRepository: UserRepository) : WebSecurityConfigurerAdapter() {
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    override fun userDetailsService(): UserDetailsService {
        return UserDetailsService { username ->
            val username = username ?: throw UsernameNotFoundException(username)
            val u = userRepository.findByUsername<UserRepository.AuthProjection>(username)
                    ?: throw UsernameNotFoundException(username)

            User.builder()
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

    @Bean
    fun jwtTokenProvider(userDetailsService: UserDetailsService): JwtTokenProvider {
        return JwtTokenProvider(userDetailsService)
    }

    override fun configure(http: HttpSecurity) {
        http.csrf().disable()

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        http
                .authorizeRequests()
                .antMatchers("/", "/user/auth").permitAll()
                .anyRequest().authenticated()

        http.exceptionHandling().accessDeniedPage("/login")
        http.apply(JwtTokenFilterConfigurer(jwtTokenProvider(userDetailsService())))
    }

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.userDetailsService(userDetailsService())?.passwordEncoder(passwordEncoder())
    }

    @Bean("authenticationManager")
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }
}
