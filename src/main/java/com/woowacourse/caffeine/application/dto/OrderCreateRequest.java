package com.woowacourse.caffeine.application.dto;

public class OrderCreateRequest {

    private long menuItemId;
    private String customerId;

    public OrderCreateRequest() {
    }

    public OrderCreateRequest(final long menuItemId, final String customerId) {
        this.menuItemId = menuItemId;
        this.customerId = customerId;
    }

    public long getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(final long menuItemId) {
        this.menuItemId = menuItemId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(final String customerId) {
        this.customerId = customerId;
    }
}
