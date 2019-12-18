package com.woowacourse.caffeine.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class OwnerNotFoundException extends RuntimeException {
    private static final String OWNER_NOT_FOUND_MESSAGE = "존재하지 않는 회원입니다.";

    public OwnerNotFoundException() {
        super(OWNER_NOT_FOUND_MESSAGE);
    }
}
