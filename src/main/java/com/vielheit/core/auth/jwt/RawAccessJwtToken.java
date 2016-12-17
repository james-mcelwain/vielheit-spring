package com.vielheit.core.auth.jwt;


public class RawAccessJwtToken implements JwtToken {
    private String token;

    public RawAccessJwtToken(String token) {
        this.token = token;
    }

    @Override
    public String getToken() {
        return token;
    }
}
