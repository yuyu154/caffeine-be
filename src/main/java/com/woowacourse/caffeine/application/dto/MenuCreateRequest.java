package com.woowacourse.caffeine.application.dto;

public class MenuCreateRequest {

    private String name;
    private String nameInEnglish;
    private String description;
    private int price;
    private String imgUrl;
    private String category;
    private long vendor;

    public MenuCreateRequest() {
    }

    public MenuCreateRequest(final String name, final String nameInEnglish, final String description, final int price, final String imgUrl, final String category, final long vendor) {
        this.name = name;
        this.nameInEnglish = nameInEnglish;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
        this.category = category;
        this.vendor = vendor;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getNameInEnglish() {
        return nameInEnglish;
    }

    public void setNameInEnglish(final String nameInEnglish) {
        this.nameInEnglish = nameInEnglish;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(final int price) {
        this.price = price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(final String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(final String category) {
        this.category = category;
    }

    public long getVendor() {
        return vendor;
    }

    public void setVendor(final long vendor) {
        this.vendor = vendor;
    }
}
