package com.woowacourse.caffeine.application.service;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

@Service
public class ShopNotificationService {

    private final NotificationInternalService<Long> notificationInternalService = new NotificationInternalService<>();

    public ResponseBodyEmitter subscribe(final long shopId) {
        return notificationInternalService.subscribe(shopId);
    }

    public void send(final long shopId, final String message) {
        notificationInternalService.send(shopId, message);
    }
}
