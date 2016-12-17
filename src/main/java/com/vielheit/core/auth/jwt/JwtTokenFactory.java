package com.vielheit.core.auth.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.vielheit.core.auth.UserContext;
import com.vielheit.core.utility.KeyReader;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPrivateKey;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtTokenFactory {
    private Logger log = Logger.getLogger(JwtTokenFactory.class);

    RSAPrivateKey privateKey;

    public JwtTokenFactory() {
        try {
            privateKey = (RSAPrivateKey) KeyReader.readPrivateKey();
            log.info("Private key loaded");
        } catch (Exception ex) {
            log.error("Could not read private key into memory", ex);
        }
    }


    public JwtToken createRefreshToken(UserContext userContext) {
        String token = JWT.create()
                .withSubject(userContext.getEmail())
                .withClaim("role", userContext.getAuthority())
                .withIssuer("Vielheit")
                .withIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .withExpiresAt(Date.from(ZonedDateTime.now().plusHours(2).toInstant()))
                .sign(Algorithm.RSA256(privateKey));

        return new AccessJwtToken(token, null);
    }

    public JwtToken createAccessJwtToken(UserContext userContext) {
        String token = JWT.create()
                .withJWTId(UUID.randomUUID().toString())
                .withSubject(userContext.getEmail())
                .withClaim("role", userContext.getAuthority())
                .withIssuer("Vielheit")
                .withIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .withExpiresAt(Date.from(ZonedDateTime.now().plusHours(2).toInstant()))
                .sign(Algorithm.RSA256(privateKey));

        return new AccessJwtToken(token, null);
    }
}
