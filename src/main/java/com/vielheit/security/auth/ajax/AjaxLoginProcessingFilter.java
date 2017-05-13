package com.vielheit.security.auth.ajax;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.vielheit.core.domain.LoginAttempt;
import com.vielheit.core.repository.LoginAttemptRepository;
import com.vielheit.security.exception.AuthMethodNotSupportedException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AjaxLoginProcessingFilter extends AbstractAuthenticationProcessingFilter {
    private static Logger log = LoggerFactory.getLogger(AjaxLoginProcessingFilter.class);

    @Autowired
    LoginAttemptRepository loginAttemptRepository;

    private final AuthenticationSuccessHandler successHandler;
    private final AuthenticationFailureHandler failureHandler;

    private final ObjectMapper objectMapper;

    public AjaxLoginProcessingFilter(
            String defaultProcessUrl,
            AuthenticationSuccessHandler successHandler,
            AuthenticationFailureHandler failureHandler,
            ObjectMapper mapper
    ) {
        super(defaultProcessUrl);
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
        this.objectMapper = mapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (!HttpMethod.POST.name().equals(request.getMethod())) { // we removed is ajax from here
            log.debug("Authentication method not supported. Request method: " + request.getMethod());
            throw new AuthMethodNotSupportedException("Authentication method not supported");
        }

        try {
            LoginRequest loginRequest = objectMapper.readValue(request.getReader(), LoginRequest.class);
            LoginAttempt loginAttempt = new LoginAttempt();
            loginAttempt.setEmailAddress(loginRequest.getEmailAddress());

            String inetAddress = request.getHeader("X-FORWARDED-FOR");
            if (inetAddress == null) {
                inetAddress = request.getRemoteAddr();
            }
            loginAttempt.setInetAddress(inetAddress);
            loginAttemptRepository.save(loginAttempt);

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
}
