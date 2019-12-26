package com.woowacourse.caffeine.application.exception;

import org.springframework.http.HttpStatus;

public class InvalidSearchRequestException extends ErrorResponseException {
    private static final String INVALID_SEARCH_REQUEST_MESSAGE = "올바른 검색 요청이 아닙니다.";

    public InvalidSearchRequestException() {
        super(INVALID_SEARCH_REQUEST_MESSAGE, HttpStatus.BAD_REQUEST);
    }
}
