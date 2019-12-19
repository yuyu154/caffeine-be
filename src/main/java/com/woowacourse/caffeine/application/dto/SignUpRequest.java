package com.woowacourse.caffeine.application.dto;

public class SignUpRequest {
    private String email;
    private String password;
    private String shopName;
    private String shopAddress;

    public SignUpRequest(final String email, final String password, final String shopName, final String shopAddress) {
        this.email = email;
        this.password = password;
        this.shopName = shopName;
        this.shopAddress = shopAddress;
    }

    public SignUpRequest() {
    }

    public String getShopName() {
        return shopName;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
