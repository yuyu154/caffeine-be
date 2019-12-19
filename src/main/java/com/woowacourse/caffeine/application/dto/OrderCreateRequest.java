package com.woowacourse.caffeine.application.dto;

import java.util.List;

public class OrderCreateRequest {

    private List<Long> menuItemIds;

    public OrderCreateRequest() {
    }

    public OrderCreateRequest(List<Long> menuItemIds) {
        this.menuItemIds = menuItemIds;
    }

    public List<Long> getMenuItemIds() {
        return menuItemIds;
    }

    public void setMenuItemIds(final List<Long> menuItemIds) {
        this.menuItemIds = menuItemIds;
    }
}
