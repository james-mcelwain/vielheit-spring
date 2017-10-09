package com.vielheit.security.auth.ajax;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vielheit.core.repository.LoginAttemptRepository;
import com.vielheit.security.config.WebSecurityConfig;
import com.vielheit.security.exception.AuthMethodNotSupportedException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.session.SessionProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.inject.Inject;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.InternalServerErrorException;
import java.io.IOException;
import java.util.Objects;

import static java.util.Objects.nonNull;

public class AjaxLoginProcessingFilter extends AbstractAuthenticationProcessingFilter {
    private Logger log = LoggerFactory.getLogger(AjaxLoginProcessingFilter.class);

    private AuthenticationSuccessHandler successHandler;
    private AuthenticationFailureHandler failureHandler;
    private ObjectMapper objectMapper;
    private RedisTemplate<String, String> redisTemplate;

    public AjaxLoginProcessingFilter(
            String defaultProcessUrl,
            AuthenticationSuccessHandler successHandler,
            AuthenticationFailureHandler failureHandler,
            ObjectMapper mapper,
            RedisTemplate<String, String> redisTemplate
    ) {
        super(defaultProcessUrl);
        this.successHandler = Objects.requireNonNull(successHandler);
        this.failureHandler = Objects.requireNonNull(failureHandler);
        this.objectMapper = Objects.requireNonNull(mapper);
        this.redisTemplate = Objects.requireNonNull(redisTemplate);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper((HttpServletRequest) req);
        super.doFilter(requestWrapper, res, chain);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (!HttpMethod.POST.name().equals(request.getMethod())) { // we removed is ajax from here
            log.debug("Authentication method not supported. Request method: " + request.getMethod());
            throw new AuthMethodNotSupportedException("Authentication method not supported");
        }

        try {
            LoginRequest loginRequest = objectMapper.readValue(request.getReader(), LoginRequest.class);

            String inetAddress = request.getHeader("X-FORWARDED-FOR");
            if (inetAddress == null) {
                inetAddress = request.getRemoteAddr();
            }

            if (checkBanned(inetAddress)) {
                throw new InternalServerErrorException();
            }

            if (StringUtils.isBlank(loginRequest.getEmailAddress()) || StringUtils.isBlank(loginRequest.getPassword())) {
                throw new AuthenticationServiceException("Username or Password not provided");
            }

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginRequest.getEmailAddress(), loginRequest.getPassword());

            return this.getAuthenticationManager().authenticate(token);
        } catch (JsonMappingException jmex) {
            logger.error("Malformed login request " + jmex);
        }
        response.setStatus(401);
        return null;
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult
    ) throws IOException, ServletException {
        successHandler.onAuthenticationSuccess(request, response, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException failed
    ) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        failureHandler.onAuthenticationFailure(request, response, failed);
    }

    private boolean checkBanned(String inetAddress) {
        final String key = "la:" + inetAddress;
        String timesAttempted = redisTemplate.opsForValue().get(key);
        if (nonNull(timesAttempted)) {
            return Integer.valueOf(timesAttempted) >= WebSecurityConfig.LOGIN_ATTEMPT_LIMIT;
        }

        return false;
    }
}
