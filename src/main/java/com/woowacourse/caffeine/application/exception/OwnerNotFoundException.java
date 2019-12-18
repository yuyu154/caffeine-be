package com.woowacourse.caffeine.application.exception;

import org.springframework.http.HttpStatus;

public class OwnerNotFoundException extends ErrorResponseException {
    private static final String OWNER_NOT_FOUND_MESSAGE = "존재하지 않는 회원입니다.";

    public OwnerNotFoundException() {
        super(OWNER_NOT_FOUND_MESSAGE, HttpStatus.BAD_REQUEST);
    }
}
