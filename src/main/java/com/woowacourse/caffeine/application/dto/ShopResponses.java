package com.woowacourse.caffeine.application.dto;

import java.util.List;

public class ShopResponses {
    private List<ShopResponse> shopResponses;

    public ShopResponses() {
    }

    public ShopResponses(final List<ShopResponse> shopResponses) {
        this.shopResponses = shopResponses;
    }

    public List<ShopResponse> getShopResponses() {
        return shopResponses;
    }

    public void setShopResponses(List<ShopResponse> shopResponses) {
        this.shopResponses = shopResponses;
    }
}
