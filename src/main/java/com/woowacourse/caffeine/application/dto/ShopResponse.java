package com.woowacourse.caffeine.application.dto;

public class ShopResponse {

    public long id;
    public String name;

    public ShopResponse() {
    }

    public ShopResponse(final long id, final String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
