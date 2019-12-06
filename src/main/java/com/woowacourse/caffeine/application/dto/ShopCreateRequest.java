package com.woowacourse.caffeine.application.dto;

public class ShopCreateRequest {

    private String name;

    public ShopCreateRequest() {
    }

    public ShopCreateRequest(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
