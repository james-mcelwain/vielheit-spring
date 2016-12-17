package com.vielheit.core.auth.jwt;

public interface  TokenExtractor {
    String extract(String payload);
}
