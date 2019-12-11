package com.woowacourse.caffeine.application.exception;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(final long orderId) {
        super(String.format("해당 주문이 없습니다, ID: %d", orderId));
    }
}
