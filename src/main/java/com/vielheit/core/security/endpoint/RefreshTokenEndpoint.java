package com.vielheit.core.security.endpoint;

import com.vielheit.core.domain.User;
import com.vielheit.core.repository.UserRepository;
import com.vielheit.core.security.auth.jwt.extractor.TokenExtractor;
import com.vielheit.core.security.auth.jwt.verifier.TokenVerifier;
import com.vielheit.core.security.config.JwtSettings;
import com.vielheit.core.security.config.WebSecurityConfig;
import com.vielheit.core.security.exceptions.InvalidJwtToken;
import com.vielheit.core.security.model.UserContext;
import com.vielheit.core.security.model.token.JwtToken;
import com.vielheit.core.security.model.token.JwtTokenFactory;
import com.vielheit.core.security.model.token.RawAccessJwtToken;
import com.vielheit.core.security.model.token.RefreshToken;
import com.vielheit.core.service.UserService;
import com.vielheit.core.utility.KeyReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class RefreshTokenEndpoint {
    @Autowired private JwtTokenFactory tokenFactory;
    @Autowired private UserService userService;
    @Autowired private TokenVerifier tokenVerifier;
    @Autowired @Qualifier("jwtHeaderTokenExtractor") private TokenExtractor tokenExtractor;

    @RequestMapping(value="/api/auth/token", method=RequestMethod.GET, produces={ MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody JwtToken refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String tokenPayload = tokenExtractor.extract(request.getHeader(WebSecurityConfig.JWT_TOKEN_HEADER_PARAM));

        RawAccessJwtToken rawToken = new RawAccessJwtToken(tokenPayload);
        RefreshToken refreshToken = RefreshToken.create(rawToken, KeyReader.readPrivateKey()).orElseThrow(InvalidJwtToken::new);

        String jti = refreshToken.getJti();
        if (!tokenVerifier.verify(jti)) {
            throw new InvalidJwtToken();
        }

        String subject = refreshToken.getSubject();
        Optional<User> user = userService.getByEmailAddress(subject);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("User not foud" + subject);
        }

        if (user.get().getRoles() == null) throw new InsufficientAuthenticationException("User has no roles assigned");
        List<GrantedAuthority> authorities = user.get().getRoles().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getRole().authority()))
                .collect(Collectors.toList());

        UserContext userContext = UserContext.create(user.get().getEmailAddress(), authorities);

        return tokenFactory.createAccessJwtToken(userContext);
    }
}
