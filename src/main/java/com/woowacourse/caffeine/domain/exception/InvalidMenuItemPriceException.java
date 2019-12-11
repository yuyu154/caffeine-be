package com.woowacourse.caffeine.domain.exception;

public class InvalidMenuItemPriceException extends RuntimeException {

    public InvalidMenuItemPriceException(final int actualPrice) {
        super(String.format("올바르지 않은 가격입니다: %d", actualPrice));
    }
}
