package com.woowacourse.caffeine.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class InvalidPasswordException extends RuntimeException {
    private static final String INVALID_PASSWORD_MESSAGE = "패스워드는 숫자,특수문자 1개이상, 영문은 2개이상 총 8글자 이상이여야 합니다.";

    public InvalidPasswordException() {
        super(INVALID_PASSWORD_MESSAGE);
    }
}
