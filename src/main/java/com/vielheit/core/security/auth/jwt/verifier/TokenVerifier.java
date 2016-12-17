package com.vielheit.core.security.auth.jwt.verifier;

public interface TokenVerifier {
    boolean verify(String jti);
}
