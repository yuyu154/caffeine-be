package com.woowacourse.caffeine.application.dto;

public class MenuItemResponse {

    private final long id;
    private final String name;
    private final String description;
    private final int price;

    public MenuItemResponse(final long id, final String name, final String description, final int price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }
}
