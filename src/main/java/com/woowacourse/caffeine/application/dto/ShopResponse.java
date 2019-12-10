package com.woowacourse.caffeine.application.dto;

public class ShopResponse {
    private Long id;
    private String name;
    private String image;
    private String address;
    private String phoneNumber;

    public ShopResponse() {
    }

    public ShopResponse(final Long id, final String name, final String image, final String address, final String phoneNumber) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
