package com.woowacourse.caffeine.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DuplicateLoginException extends RuntimeException {
    private static final String DUPLICATE_LOGIN_MESSAGE = "중복 로그인은 허용되지 않습니다.";

    public DuplicateLoginException() {
        super(DUPLICATE_LOGIN_MESSAGE);
    }
}
