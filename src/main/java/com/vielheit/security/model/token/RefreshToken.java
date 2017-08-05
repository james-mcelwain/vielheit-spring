package com.vielheit.security.model.token;

import java.security.PrivateKey;
import java.util.List;
import java.util.Optional;

import com.vielheit.security.model.Scopes;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

@SuppressWarnings("unchecked")
public class RefreshToken implements JwtToken {
    private Jws<Claims> claims;

    private RefreshToken(Jws<Claims> claims) {
        this.claims = claims;
    }

    public static Optional<RefreshToken> create(RawAccessJwtToken token, PrivateKey signingKey) {
        Jws<Claims> claims = token.parseClaims(signingKey);

        List<String> scopes = claims.getBody().get("scopes", List.class);
        if (scopes == null || scopes.isEmpty() 
                || scopes.stream().noneMatch(scope -> Scopes.REFRESH_TOKEN.authority().equals(scope))) {
            return Optional.empty();
        }

        return Optional.of(new RefreshToken(claims));
    }

    @Override
    public String getToken() {
        return null;
    }

    public Jws<Claims> getClaims() {
        return claims;
    }
    
    public String getJti() {
        return claims.getBody().getId();
    }
    
    public String getSubject() {
        return claims.getBody().getSubject();
    }
}
