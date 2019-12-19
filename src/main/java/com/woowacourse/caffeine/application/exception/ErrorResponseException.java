package com.woowacourse.caffeine.application.exception;

import org.springframework.http.HttpStatus;

public class ErrorResponseException extends RuntimeException {

    private final HttpStatus status;

    public ErrorResponseException(final String message, final HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
