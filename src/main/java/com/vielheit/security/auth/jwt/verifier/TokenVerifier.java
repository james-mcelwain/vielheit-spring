package com.vielheit.security.auth.jwt.verifier;

public interface TokenVerifier {
    boolean verify(String jti);
}
