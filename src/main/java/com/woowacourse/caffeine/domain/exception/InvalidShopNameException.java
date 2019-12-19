package com.woowacourse.caffeine.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidShopNameException extends RuntimeException {

    public InvalidShopNameException(final String actualName) {
        super(String.format("올바르지 않은 매장 이름입니다: %s", actualName));
    }
}
