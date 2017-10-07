package com.vielheit.security.controller;

import com.vielheit.core.domain.User;
import com.vielheit.core.exception.ApplicationException;
import com.vielheit.core.service.UserService;
import com.vielheit.security.auth.jwt.extractor.TokenExtractor;
import com.vielheit.security.auth.jwt.verifier.TokenVerifier;
import com.vielheit.security.config.WebSecurityConfig;
import com.vielheit.security.exception.InvalidJwtToken;
import com.vielheit.security.model.UserContext;
import com.vielheit.security.model.token.JwtToken;
import com.vielheit.security.model.token.JwtTokenFactory;
import com.vielheit.security.model.token.RawAccessJwtToken;
import com.vielheit.security.model.token.RefreshToken;
import com.vielheit.security.utility.KeyReader;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@Path("/api/auth/token")
public class TokenController {
    private Logger log = Logger.getLogger(TokenController.class);

    private JwtTokenFactory tokenFactory;
    private UserService userService;
    private TokenVerifier tokenVerifier;
    private TokenExtractor tokenExtractor;

    @Inject
    public TokenController(
            @NotNull JwtTokenFactory jwtTokenFactory,
            @NotNull UserService userService,
            @NotNull TokenVerifier tokenVerifier,
            @NotNull @Qualifier("jwtHeaderTokenExtractor") TokenExtractor tokenExtractor
    ) {
        this.tokenFactory = Objects.requireNonNull(jwtTokenFactory);
        this.userService = Objects.requireNonNull(userService);
        this.tokenVerifier = Objects.requireNonNull(tokenVerifier);
        this.tokenExtractor = Objects.requireNonNull(tokenExtractor);
    }

    @GET
    public JwtToken refreshToken(@Context HttpHeaders headers) throws ApplicationException {
        String payload = tokenExtractor.extract(headers.getHeaderString(WebSecurityConfig.JWT_TOKEN_HEADER_PARAM));

        RawAccessJwtToken rawAccessJwtToken = new RawAccessJwtToken(payload);
        RefreshToken refreshToken = RefreshToken.create(rawAccessJwtToken, KeyReader.readPrivateKey()).orElseThrow(InvalidJwtToken::new);

        String jti = refreshToken.getJti();

        if (!tokenVerifier.verify(jti)) {
            throw new InvalidJwtToken();
        }

        String subject = refreshToken.getSubject();
        User user = userService.getById(Long.valueOf(subject)).orElseThrow(() -> new UsernameNotFoundException("User not found: " + subject));

        if (user.getRoles() == null)
            throw new InsufficientAuthenticationException("User has no roles assigned");

        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getRole().authority()))
                .collect(Collectors.toList());

        UserContext userContext = UserContext.create(user.getId(), authorities);

        return tokenFactory.createAccessJwtToken(userContext);
    }

}
