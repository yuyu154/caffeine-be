package com.woowacourse.caffeine.application.dto;

public class OrderCreateRequest {

    private long menuItemId;

    public OrderCreateRequest() {
    }

    public OrderCreateRequest(final long menuItemId) {
        this.menuItemId = menuItemId;
    }

    public long getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(final long menuItemId) {
        this.menuItemId = menuItemId;
    }
}
