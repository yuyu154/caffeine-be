package com.woowacourse.caffeine.application.dto;

import java.util.List;

public class OrderCreateRequest {

    private long menuItemId;
    private String customerId;
    private List<Long> menuItems;

    public OrderCreateRequest() {
    }

    public OrderCreateRequest(final long menuItemId, final String customerId) {
        this.menuItemId = menuItemId;
        this.customerId = customerId;
    }

    public OrderCreateRequest(final String customerId, final List<Long> menuItems) {
        this.customerId = customerId;
        this.menuItems = menuItems;
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

    public List<Long> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(final List<Long> menuItems) {
        this.menuItems = menuItems;
    }
}
