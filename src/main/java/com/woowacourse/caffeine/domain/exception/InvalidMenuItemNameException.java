package com.woowacourse.caffeine.domain.exception;

public class InvalidMenuItemNameException extends RuntimeException {

    public InvalidMenuItemNameException(final String actualName) {
        super(String.format("올바르지 않은 메뉴 이름입니다: %s", actualName));
    }
}
