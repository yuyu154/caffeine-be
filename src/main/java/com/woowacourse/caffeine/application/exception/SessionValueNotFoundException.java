package com.woowacourse.caffeine.application.exception;

import org.springframework.http.HttpStatus;

public class SessionValueNotFoundException extends ErrorResponseException {
    private static final String INVALID_LOGOUT_REQUEST_MESSAGE = "로그인 한 유저가 아닙니다.";

    public SessionValueNotFoundException() {
        super(INVALID_LOGOUT_REQUEST_MESSAGE, HttpStatus.UNAUTHORIZED);
    }
}
