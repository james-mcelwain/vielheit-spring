package com.vielheit.security.auth.ajax;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vielheit.core.domain.LoginAttempt;
import com.vielheit.core.domain.User;
import com.vielheit.core.exception.ApplicationException;
import com.vielheit.core.repository.LoginAttemptRepository;
import com.vielheit.core.service.UserService;
import com.vielheit.security.model.UserContext;
import org.apache.log4j.Logger;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.InternalServerErrorException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class AjaxAuthenticationProvider implements AuthenticationProvider {
    private Logger log = Logger.getLogger(AjaxAuthenticationProvider.class);

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private LoginAttemptRepository loginAttemptRepository;
    private UserService userService;

    private final int LOGIN_THROTTLE = 5;

    @Inject
    public AjaxAuthenticationProvider(
            @NotNull LoginAttemptRepository loginAttemptRepository,
            @NotNull UserService userService
    ) {
        this.loginAttemptRepository = Objects.requireNonNull(loginAttemptRepository);
        this.userService = Objects.requireNonNull(userService);

    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        Assert.notNull(authentication, "No authentication data provided");

        String emailAddress = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        List<LoginAttempt> attempts = loginAttemptRepository.getRecentLoginAttempts(emailAddress, LocalDateTime.now().minusMinutes(LOGIN_THROTTLE), LocalDateTime.now());
        log.info("Attempting to authenticate " + emailAddress + " attempts " + attempts.size());

        User user;
        try {
            user = userService.getByEmailAddress(emailAddress).orElseThrow(() -> new UsernameNotFoundException("User not found: " + emailAddress));
        } catch (ApplicationException apex) {
            log.error(apex.getMessage());
            throw new InternalServerErrorException();
        }

        if (!encoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Authentication Failed. Username or Password not valid.");
        }

        if (user.getRoles() == null) {
            throw new InsufficientAuthenticationException("User has no roles assigned");
        }

        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getRole().authority()))
                .collect(Collectors.toList());

        UserContext userContext = UserContext.create(user.getId(), authorities);

        return new UsernamePasswordAuthenticationToken(userContext, null, userContext.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

