package com.vielheit.core.security.auth.ajax;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginRequest {
    private String emailAddress;
    private String password;

    @JsonCreator
    public LoginRequest(@JsonProperty("emailAddress") String emailAddress, @JsonProperty("password") String password) {
        this.emailAddress = emailAddress;
        this.password = password;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPassword() {
        return password;
    }
}
