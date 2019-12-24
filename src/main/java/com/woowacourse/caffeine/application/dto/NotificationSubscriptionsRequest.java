package com.woowacourse.caffeine.application.dto;

public class NotificationSubscriptionsRequest {
    private String token;

    public NotificationSubscriptionsRequest() {
    }

    public NotificationSubscriptionsRequest(final String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
