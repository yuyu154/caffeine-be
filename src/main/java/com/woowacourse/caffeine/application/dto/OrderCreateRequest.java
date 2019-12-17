package com.woowacourse.caffeine.application.dto;

import java.util.List;

public class OrderCreateRequest {

    private long menuItemId;
    private String customerId;
    private List<Long> menuItemIds;

    public OrderCreateRequest() {
    }

    public OrderCreateRequest(final long menuItemId, final String customerId) {
        this.menuItemId = menuItemId;
        this.customerId = customerId;
    }

    public OrderCreateRequest(final String customerId, final List<Long> menuItemIds) {
        this.customerId = customerId;
        this.menuItemIds = menuItemIds;
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

    public List<Long> getMenuItemIds() {
        return menuItemIds;
    }

    public void setMenuItemIds(final List<Long> menuItemIds) {
        this.menuItemIds = menuItemIds;
    }
}
