package com.woowacourse.caffeine.domain.exception;

import com.woowacourse.caffeine.domain.OrderStatus;

public class InvalidOrderStatusChangeException extends RuntimeException {

    public InvalidOrderStatusChangeException(final OrderStatus current, final OrderStatus desired) {
        super(String.format("올바르지 않은 주문 상태 변경입니다: %s → %s", current.name(), desired.name()));
    }
}
