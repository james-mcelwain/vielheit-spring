package com.vielheit.security.auth.ajax;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

import com.vielheit.core.domain.Login;
import com.vielheit.core.exception.ApplicationException;
import com.vielheit.core.repository.LoginRepository;
import com.vielheit.core.service.UserService;
import com.vielheit.security.model.UserContext;
import com.vielheit.security.model.token.JwtToken;
import com.vielheit.security.model.token.JwtTokenFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class AjaxAwareAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private Logger logger = LoggerFactory.getLogger(AjaxAwareAuthenticationSuccessHandler.class);

    private ObjectMapper mapper;
    private JwtTokenFactory tokenFactory;
    private UserService userService;

    @Inject
    public AjaxAwareAuthenticationSuccessHandler(
            @NotNull ObjectMapper objectMapper,
            @NotNull JwtTokenFactory tokenFactory,
            @NotNull UserService userService
    ) {
        this.mapper = Objects.requireNonNull(objectMapper);
        this.tokenFactory = Objects.requireNonNull(tokenFactory);
        this.userService = Objects.requireNonNull(userService);
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        UserContext userContext = (UserContext) authentication.getPrincipal();
        
        JwtToken accessToken = tokenFactory.createAccessJwtToken(userContext);
        JwtToken refreshToken = tokenFactory.createRefreshToken(userContext);
        
        Map<String, Object> authResponsePayload = new HashMap<>();
        authResponsePayload.put("token", accessToken.getToken());
        authResponsePayload.put("refreshToken", refreshToken.getToken());

        try {
            userService.getById(userContext.getUserId())
                    .ifPresent(u -> {
                        authResponsePayload.put("user", u);
                    });
        } catch (ApplicationException apex) {
            throw new ServletException();
        }

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        mapper.writeValue(response.getWriter(), authResponsePayload);

        clearAuthenticationAttributes(request);
    }

    /**
     * Removes temporary authentication-related data which may have been stored
     * in the session during the authentication process..
     * 
     */
    protected final void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            return;
        }

        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}
