package com.woowacourse.caffeine.application.dto;

public class ShopResponse {
    public long id;
    public String name;

    // image url
    private String image;
    private String address;
    private String phoneNumber;

    public ShopResponse() {
    }

    public ShopResponse(final long id, final String name) {
        this.id = id;
        this.name = name;
    }

    public ShopResponse(long id, String name, String image, String address, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.address = address;
        this.phoneNumber = phoneNumber;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
