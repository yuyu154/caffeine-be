package com.woowacourse.caffeine.application.dto;

public class OrderResponse {

    private long id;
    private long menuItemId;

    public OrderResponse() {
    }

    public OrderResponse(final long id, final long menuItemId) {
        this.id = id;
        this.menuItemId = menuItemId;
    }

    public long getId() {
        return id;
    }

    public long getMenuItemId() {
        return menuItemId;
    }

    @Override
    public String toString() {
        return "OrderResponse{" +
                "id=" + id +
                ", menuItemId=" + menuItemId +
                '}';
    }
}
