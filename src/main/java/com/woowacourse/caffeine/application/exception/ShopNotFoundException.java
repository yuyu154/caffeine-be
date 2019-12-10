package com.woowacourse.caffeine.application.exception;

public class ShopNotFoundException extends RuntimeException {

    public ShopNotFoundException(final long actualId) {
        super(String.format("매장을 찾을 수 없습니다, ID: %d", actualId));
    }
}
