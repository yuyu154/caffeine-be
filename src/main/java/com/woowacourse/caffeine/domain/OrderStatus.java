package com.woowacourse.caffeine.domain;

public enum OrderStatus {
    PENDING,
    IN_PROGRESS,
    REJECTED,
    FINISHED;

    public static OrderStatus from(String name) {
        return valueOf(name.toUpperCase());
    }
}

