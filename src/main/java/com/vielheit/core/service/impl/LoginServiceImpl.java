package com.vielheit.core.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vielheit.core.domain.Login;
import com.vielheit.core.domain.LoginAttempt;
import com.vielheit.core.repository.LoginAttemptRepository;
import com.vielheit.core.repository.LoginRepository;
import com.vielheit.core.service.LoginService;
import com.vielheit.security.auth.ajax.LoginRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static java.util.Objects.nonNull;

@Component
public class LoginServiceImpl implements LoginService {
    private final static Logger logger = LoggerFactory.getLogger(LoginService.class);

    private static int LOGIN_ATTEMPT_LIMIT = 10;
    private static int LOGIN_ATTEMPT_TIMEOUT = 60;

    private LoginRepository loginRepository;
    private LoginAttemptRepository loginAttemptRepository;
    private RedisTemplate<String, String> redisTemplate;

    public LoginServiceImpl(
            @NotNull LoginRepository loginRepository,
            @NotNull LoginAttemptRepository loginAttemptRepository,
            @NotNull RedisTemplate<String, String> redisTemplate
    ) {
        this.loginRepository = Objects.requireNonNull(loginRepository);
        this.loginAttemptRepository = Objects.requireNonNull(loginAttemptRepository);
        this.redisTemplate = Objects.requireNonNull(redisTemplate);
    }

    public void createLogin(HttpServletRequest request, Long userId) {
        Login login = new Login();
        login.setUserId(userId);
        login.setInetAddress(getInetAddress(request));
        loginRepository.save(login);
    }

    public void createLoginAttempt(HttpServletRequest request, String emailAddress, LoginAttempt.FailureReason reason) {
        LoginAttempt loginAttempt = new LoginAttempt();
        String inetAddress = getInetAddress(request);

        loginAttempt.setEmailAddress(emailAddress);
        loginAttempt.setInetAddress(inetAddress);
        loginAttempt.setFailureReason(reason);
        loginAttemptRepository.save(loginAttempt);
        incrementAttemptCount(inetAddress);
    }

    public boolean isBanned(String inetAddress) {
        String count = redisTemplate.opsForValue().get(redisKey(inetAddress));
        return nonNull(count) && Integer.valueOf(count) >= LOGIN_ATTEMPT_LIMIT;
    }

    public boolean isBanned(HttpServletRequest request) {
        return isBanned(getInetAddress(request));
    }

    private void incrementAttemptCount(String inetAddress) {
        final String key = redisKey(inetAddress);
        redisTemplate.opsForValue().setIfAbsent(key, "0");
        redisTemplate.opsForValue().increment(key, 1);
        redisTemplate.expire(key, LOGIN_ATTEMPT_TIMEOUT, TimeUnit.SECONDS);
    }

    private String getInetAddress(HttpServletRequest request) {
        String inetAddress = request.getHeader("X-FORWARDED-FOR");
        if (inetAddress == null) {
            inetAddress = request.getRemoteAddr();
        }

        return inetAddress;
    }

    private String redisKey(String inetAddress) {
        return "la:" + inetAddress;
    }
}
