package com.woowacourse.caffeine.application.dto;

public class OwnerResponse {
    private long id;
    private String email;
    private ShopResponse shop;

    public OwnerResponse(final long id, final String email, final ShopResponse shop) {
        this.id = id;
        this.email = email;
        this.shop = shop;
    }

    public OwnerResponse() {
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public ShopResponse getShop() {
        return shop;
    }
}
