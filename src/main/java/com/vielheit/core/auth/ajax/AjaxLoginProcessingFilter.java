package com.vielheit.core.auth.ajax;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AjaxLoginProcessingFilter extends AbstractAuthenticationProcessingFilter {
    private static Logger logger = Logger.getLogger(AjaxLoginProcessingFilter.class);

    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    private final AuthenticationFailureHandler authenticationFailureHandler;

    private final ObjectMapper objectMapper;

    private static class LoginRequest {
        private String email;
        private String password;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public AjaxLoginProcessingFilter(
            String defaultFilterProcessesUrl,
            AuthenticationSuccessHandler authenticationSuccessHandler,
            AuthenticationFailureHandler authenticationFailureHandler,
            ObjectMapper objectMapper) {
        super(defaultFilterProcessesUrl);
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        LoginRequest loginRequest = objectMapper.readValue(httpServletRequest.getReader(), LoginRequest.class);

        if (StringUtils.isBlank(loginRequest.getEmail()) || StringUtils.isBlank(loginRequest.getPassword())) {
            throw new AuthenticationServiceException("Username or Password not provided");
        }

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());

        return this.getAuthenticationManager().authenticate(token);
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            FilterChain filterChain,
            Authentication authResult) throws IOException, ServletException {
        authenticationSuccessHandler.onAuthenticationSuccess(httpServletRequest, httpServletResponse, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        authenticationFailureHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse, failed);
    }
}
