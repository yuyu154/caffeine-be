package com.woowacourse.caffeine.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class InvalidPasswordException extends RuntimeException {
    private static final String INVALID_PASSWORD_MESSAGE = "올바르지 않은 패스워드 입니다.";

    public InvalidPasswordException() {
        super(INVALID_PASSWORD_MESSAGE);
    }
}
