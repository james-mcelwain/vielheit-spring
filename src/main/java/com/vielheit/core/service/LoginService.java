package com.vielheit.core.service;

import com.vielheit.core.domain.LoginAttempt;

import javax.servlet.http.HttpServletRequest;

public interface LoginService {
    void createLogin(HttpServletRequest request, Long userId);
    void createLoginAttempt(HttpServletRequest request, String emailAddress, LoginAttempt.FailureReason reason);
    boolean isBanned(String inetAddress);
    boolean isBanned(HttpServletRequest request);
}
