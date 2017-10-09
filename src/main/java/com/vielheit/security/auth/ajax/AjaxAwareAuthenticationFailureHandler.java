package com.vielheit.security.auth.ajax;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vielheit.core.domain.LoginAttempt;
import com.vielheit.core.exception.ErrorCode;
import com.vielheit.core.exception.ErrorResponse;
import com.vielheit.core.repository.LoginAttemptRepository;
import com.vielheit.security.config.WebSecurityConfig;
import com.vielheit.security.exception.AuthMethodNotSupportedException;
import com.vielheit.security.exception.JwtExpiredTokenException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
public class AjaxAwareAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private ObjectMapper mapper;
    private LoginAttemptRepository loginAttemptRepo;
    private RedisTemplate<String, String> redis;

    @Inject
    public AjaxAwareAuthenticationFailureHandler(
            @NotNull ObjectMapper mapper,
            @NotNull LoginAttemptRepository loginAttemptRepo,
            @NotNull RedisTemplate<String, String> redis
    ) {
        this.mapper = Objects.requireNonNull(mapper);
        this.loginAttemptRepo = Objects.requireNonNull(loginAttemptRepo);
        this.redis = Objects.requireNonNull(redis);
    }

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException e
    ) throws IOException, ServletException {

        LoginRequest loginRequest = mapper.readValue(((ContentCachingRequestWrapper) request).getContentAsByteArray(), LoginRequest.class);
        LoginAttempt loginAttempt = new LoginAttempt();
        loginAttempt.setEmailAddress(loginRequest.getEmailAddress());

        String inetAddress = request.getHeader("X-FORWARDED-FOR");
        if (inetAddress == null) {
            inetAddress = request.getRemoteAddr();
        }

        loginAttempt.setInetAddress(inetAddress);
        loginAttemptRepo.save(loginAttempt);

        setInRedis(inetAddress);

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        if (e instanceof BadCredentialsException) {
            mapper.writeValue(response.getWriter(), ErrorResponse.of("Invalid username or password", ErrorCode.AUTHENTICATION));
        } else if (e instanceof JwtExpiredTokenException) {
            mapper.writeValue(response.getWriter(), ErrorResponse.of("Token has expired", ErrorCode.JWT_TOKEN_EXPIRED));
        } else if (e instanceof AuthMethodNotSupportedException) {
            mapper.writeValue(response.getWriter(), ErrorResponse.of(e.getMessage(), ErrorCode.AUTHENTICATION));
        }

        mapper.writeValue(response.getWriter(), ErrorResponse.of("Authentication failed", ErrorCode.AUTHENTICATION));
    }

    private void setInRedis(String inetAddress) {
        final String key = "la:" + inetAddress;
        redis.opsForValue().setIfAbsent(key, "0");
        redis.opsForValue().increment(key, 1);
        redis.expire(key, WebSecurityConfig.LOGIN_ATTEMPT_TIMEOUT, TimeUnit.SECONDS);
    }
}
