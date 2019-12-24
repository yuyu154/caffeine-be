package com.woowacourse.caffeine.application.dto;

public class NotificationSendRequest {

    private String message;

    public NotificationSendRequest() {
    }

    public NotificationSendRequest(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
