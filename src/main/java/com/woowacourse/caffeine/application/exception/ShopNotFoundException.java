package com.woowacourse.caffeine.application.exception;

import org.springframework.http.HttpStatus;

public class ShopNotFoundException extends ErrorResponseException {

    public ShopNotFoundException(final long actualId) {
        super(String.format("매장을 찾을 수 없습니다, ID: %d", actualId), HttpStatus.BAD_REQUEST);
    }
}
