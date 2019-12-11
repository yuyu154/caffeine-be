package com.woowacourse.caffeine.application.dto;

public class OrderResponse {

    private long orderId;
    private long customerId;
    private long menuItemId;

    public OrderResponse() {
    }

    public OrderResponse(final long orderId, final long customerId, final long menuItemId) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.menuItemId = menuItemId;
    }

    public long getOrderId() {
        return orderId;
    }

    public long getCustomerId() {
        return customerId;
    }

    public long getMenuItemId() {
        return menuItemId;
    }
}
