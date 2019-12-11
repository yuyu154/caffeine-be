package com.woowacourse.caffeine.domain.exception;

public class InvalidMenuItemNameInEnglishException extends RuntimeException {

    public InvalidMenuItemNameInEnglishException(final String actualNameInEnglish) {
        super(String.format("올바르지 않은 메뉴 영어 이름입니다(영어만 가능합니다): %s", actualNameInEnglish));
    }
}
