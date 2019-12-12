package com.woowacourse.caffeine.application.dto;

import java.util.List;

public class OrderResponse {

    private long id;
    private String status;
    private List<MenuItemResponse> orderItems;

    public OrderResponse() {
    }

    public OrderResponse(final long id, final String status, final List<MenuItemResponse> orderItems) {
        this.id = id;
        this.status = status;
        this.orderItems = orderItems;
    }

    public long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public List<MenuItemResponse> getOrderItems() {
        return orderItems;
    }
}
