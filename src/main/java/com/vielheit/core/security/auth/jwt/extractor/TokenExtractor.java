package com.vielheit.core.security.auth.jwt.extractor;

public interface TokenExtractor {
    String extract(String payload);
}
