package com.vielheit.security.controller;

import com.vielheit.core.domain.User;
import com.vielheit.core.service.UserService;
import com.vielheit.security.auth.jwt.extractor.TokenExtractor;
import com.vielheit.security.auth.jwt.verifier.TokenVerifier;
import com.vielheit.security.config.WebSecurityConfig;
import com.vielheit.security.exceptions.InvalidJwtToken;
import com.vielheit.security.model.UserContext;
import com.vielheit.security.model.token.JwtToken;
import com.vielheit.security.model.token.JwtTokenFactory;
import com.vielheit.security.model.token.RawAccessJwtToken;
import com.vielheit.security.model.token.RefreshToken;
import com.vielheit.core.service.impl.UserServiceImpl;
import com.vielheit.security.utility.KeyReader;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Path("/api/auth/token")
public class TokenController {
    Logger log = Logger.getLogger(TokenController.class);

    @Autowired private JwtTokenFactory tokenFactory;
    @Autowired private UserService userService;
    @Autowired private TokenVerifier tokenVerifier;
    @Autowired @Qualifier("jwtHeaderTokenExtractor") private TokenExtractor tokenExtractor;

    @GET
    public JwtToken refreshToken(@Context HttpHeaders headers) {
        String payload = tokenExtractor.extract(headers.getHeaderString(WebSecurityConfig.JWT_TOKEN_HEADER_PARAM));

        RawAccessJwtToken rawAccessJwtToken = new RawAccessJwtToken(payload);
        RefreshToken refreshToken = RefreshToken.create(rawAccessJwtToken, KeyReader.readPrivateKey()).orElseThrow(InvalidJwtToken::new);

        String jti = refreshToken.getJti();

        if (!tokenVerifier.verify(jti)) {
            throw new InvalidJwtToken();
        }

        String subject = refreshToken.getSubject();
        User user = userService.getByEmailAddress(subject).orElseThrow(() -> new UsernameNotFoundException("User not found: " + subject));

        if (user.getRoles() == null)
            throw new InsufficientAuthenticationException("User has no roles assigned");
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getRole().authority()))
                .collect(Collectors.toList());

        UserContext userContext = UserContext.create(user.getId(), authorities);

        return tokenFactory.createAccessJwtToken(userContext);
    }

}
