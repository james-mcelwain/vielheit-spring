package com.vielheit.security.auth.ajax;

import com.vielheit.core.domain.LoginAttempt;
import com.vielheit.core.domain.User;
import com.vielheit.core.exception.ApplicationException;
import com.vielheit.core.exception.FakeInternalServerErrorException;
import com.vielheit.core.service.LoginService;
import com.vielheit.core.service.UserService;
import com.vielheit.security.model.UserContext;
import org.apache.log4j.Logger;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import sun.net.www.ApplicationLaunchException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import javax.ws.rs.InternalServerErrorException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AjaxAuthenticationProvider implements AuthenticationProvider {
    private Logger log = Logger.getLogger(AjaxAuthenticationProvider.class);

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private UserService userService;
    private LoginService loginService;

    @Inject
    public AjaxAuthenticationProvider(
            @NotNull UserService userService,
            @NotNull LoginService loginService
    ) {
        this.userService = Objects.requireNonNull(userService);
        this.loginService = Objects.requireNonNull(loginService);
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        Assert.notNull(authentication, "No authentication data provided");
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        if (loginService.isBanned(request)) {
            throw new AuthenticationServiceException("banned");
        }

        String emailAddress = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        User user;
        try {
            Optional<User> u = userService.getByEmailAddress(emailAddress);
            if (u.isPresent()) {
                user = u.get();
            } else {
                loginService.createLoginAttempt(request, emailAddress, LoginAttempt.FailureReason.EMAIL);
                throw new UsernameNotFoundException("Username not found: " + emailAddress);
            }
        } catch (ApplicationException apex) {
            log.error(apex.getMessage());
            loginService.createLoginAttempt(request, emailAddress, LoginAttempt.FailureReason.UNKNOWN);
            throw new InternalServerErrorException();
        }

        if (!encoder.matches(password, user.getPassword())) {
            loginService.createLoginAttempt(request, emailAddress, LoginAttempt.FailureReason.PASSWORD);
            throw new BadCredentialsException("Authentication Failed. Username or Password not valid.");
        }

        if (user.getRoles() == null) {
            loginService.createLoginAttempt(request, emailAddress, LoginAttempt.FailureReason.UNKNOWN);
            throw new InsufficientAuthenticationException("User has no roles assigned");
        }

        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getRole().authority()))
                .collect(Collectors.toList());

        UserContext userContext = UserContext.create(user.getId(), authorities);
        loginService.createLogin(request, user.getId());

        return new UsernamePasswordAuthenticationToken(userContext, null, userContext.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

