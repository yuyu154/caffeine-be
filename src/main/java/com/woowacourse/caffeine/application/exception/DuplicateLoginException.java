package com.woowacourse.caffeine.application.exception;

import org.springframework.http.HttpStatus;

public class DuplicateLoginException extends ErrorResponseException {
    private static final String DUPLICATE_LOGIN_MESSAGE = "중복 로그인은 허용되지 않습니다.";

    public DuplicateLoginException() {
        super(DUPLICATE_LOGIN_MESSAGE, HttpStatus.BAD_REQUEST);
    }
}
