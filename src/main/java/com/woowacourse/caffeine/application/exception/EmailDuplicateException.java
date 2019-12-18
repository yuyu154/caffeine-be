package com.woowacourse.caffeine.application.exception;

import org.springframework.http.HttpStatus;

public class EmailDuplicateException extends ErrorResponseException {
    private static final String EMAIL_DUPLICATE_MESSAGE = "이메일 중복은 허용되지 않습니다.";

    public EmailDuplicateException() {
        super(EMAIL_DUPLICATE_MESSAGE, HttpStatus.BAD_REQUEST);
    }
}
