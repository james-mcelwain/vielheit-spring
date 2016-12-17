package com.vielheit.core.security.model.token;

import java.security.interfaces.RSAPrivateKey;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

import com.vielheit.core.security.config.JwtSettings;
import com.vielheit.core.security.model.Scopes;
import com.vielheit.core.security.model.UserContext;
import com.vielheit.core.utility.KeyReader;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenFactory {
    private final JwtSettings settings;
    private final RSAPrivateKey privateKey;

    @Autowired
    public JwtTokenFactory(JwtSettings settings) throws Exception {
        this.settings = settings;
        this.privateKey = (RSAPrivateKey) KeyReader.readPrivateKey();
    }

    public AccessJwtToken createAccessJwtToken(UserContext userContext) {
        if (StringUtils.isBlank(userContext.getEmailAddress()))
            throw new IllegalArgumentException("Cannot create JWT Token without username");

        if (userContext.getAuthorities() == null || userContext.getAuthorities().isEmpty()) 
            throw new IllegalArgumentException("User doesn't have any privileges");

        Claims claims = Jwts.claims().setSubject(userContext.getEmailAddress());
        claims.put("scopes", userContext.getAuthorities().stream().map(Object::toString).collect(Collectors.toList()));

        String token = Jwts.builder()
          .setClaims(claims)
          .setIssuer(settings.getTokenIssuer())
          .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
          .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(settings.getTokenExpirationTime()).toInstant()))
          .signWith(SignatureAlgorithm.RS256, privateKey)
        .compact();

        return new AccessJwtToken(token, claims);
    }

    public JwtToken createRefreshToken(UserContext userContext) {
        if (StringUtils.isBlank(userContext.getEmailAddress())) {
            throw new IllegalArgumentException("Cannot create JWT Token without username");
        }

        Date currentTime = Date.from(ZonedDateTime.now().toInstant());
        Date expirationTime = Date.from(ZonedDateTime.now().plusMinutes(settings.getRefreshTokenExpTime()).toInstant());

        Claims claims = Jwts.claims().setSubject(userContext.getEmailAddress());
        claims.put("scopes", Collections.singletonList(Scopes.REFRESH_TOKEN.authority()));
        
        String token = Jwts.builder()
          .setClaims(claims)
          .setIssuer(settings.getTokenIssuer())
          .setId(UUID.randomUUID().toString())
          .setIssuedAt(currentTime)
          .setExpiration(expirationTime)
          .signWith(SignatureAlgorithm.RS256, privateKey)
        .compact();

        return new AccessJwtToken(token, claims);
    }
}
