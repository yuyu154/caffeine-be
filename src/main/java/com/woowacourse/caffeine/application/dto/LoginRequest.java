package com.woowacourse.caffeine.application.dto;

public class LoginRequest {
    private String email;
    private String password;

    public LoginRequest(final String email, final String password) {
        this.email = email;
        this.password = password;
    }

    public LoginRequest() {
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
