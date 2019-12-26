package com.woowacourse.caffeine.domain.exception;

import com.woowacourse.caffeine.application.exception.ErrorResponseException;
import org.springframework.http.HttpStatus;

public class SearchKeyWordNotFoundException extends ErrorResponseException {
    private static final String SEARCH_KEY_WORD_NOT_FOUND_MESSAGE = "올바른 검색 카테고리가 아닙니다.";

    public SearchKeyWordNotFoundException() {
        super(SEARCH_KEY_WORD_NOT_FOUND_MESSAGE, HttpStatus.BAD_REQUEST);
    }
}
