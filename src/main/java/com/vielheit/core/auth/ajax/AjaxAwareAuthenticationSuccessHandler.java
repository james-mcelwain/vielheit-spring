package com.vielheit.core.auth.ajax;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vielheit.core.auth.jwt.JwtToken;
import com.vielheit.core.auth.jwt.JwtTokenFactory;
import com.vielheit.core.auth.UserContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class AjaxAwareAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final ObjectMapper mapper;
    private final JwtTokenFactory tokenFactory;

    @Inject
    public AjaxAwareAuthenticationSuccessHandler(final ObjectMapper mapper, final JwtTokenFactory tokenFactory) {
        this.mapper = mapper;
        this.tokenFactory = tokenFactory;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            Authentication authentication) throws IOException, ServletException {
                UserContext userContext = (UserContext) authentication.getPrincipal();

        JwtToken accessToken = tokenFactory.createAccessJwtToken(userContext);
        JwtToken refreshToken = tokenFactory.createRefreshToken(userContext);

        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", accessToken.getToken());
        tokenMap.put("refreshToken", refreshToken.getToken());

        httpServletResponse.setStatus(HttpStatus.OK.value());
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        mapper.writeValue(httpServletResponse.getWriter(), tokenMap);

        clearAuthenticationAttributes(httpServletRequest);
    }

    protected final void clearAuthenticationAttributes(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession(false);

        if (session == null) {
            return;
        }

        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}
