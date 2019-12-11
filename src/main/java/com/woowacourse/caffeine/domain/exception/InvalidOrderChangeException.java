package com.woowacourse.caffeine.domain.exception;

public class InvalidOrderChangeException extends RuntimeException {

    public InvalidOrderChangeException(final String status) {
        super(String.format("%s로 주문 상태를 변경할 수 없습니다", status));
    }
}
