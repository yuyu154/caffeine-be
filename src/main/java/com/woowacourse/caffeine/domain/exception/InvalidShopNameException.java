package com.woowacourse.caffeine.domain.exception;

public class InvalidShopNameException extends RuntimeException {

    public InvalidShopNameException(final String actualName) {
        super(String.format("올바르지 않은 매장 이름입니다: %s", actualName));
    }
}
