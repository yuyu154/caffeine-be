package com.woowacourse.caffeine.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidShopAddressException extends RuntimeException {
    private static final String INVALID_SHOP_ADDRESS_MESSAGE = "올바른 주소를 입력해 주세요.";

    public InvalidShopAddressException() {
        super(INVALID_SHOP_ADDRESS_MESSAGE);
    }
}
