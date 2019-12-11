package com.woowacourse.caffeine.application.service;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

@Service
public class CustomerNotificationService {

    private final NotificationInternalService<String> notificationInternalService = new NotificationInternalService<>();

    public ResponseBodyEmitter subscribe(final String customerId) {
        return notificationInternalService.subscribe(customerId);
    }

    public void send(final String customerId, final String message) {
        notificationInternalService.send(customerId, message);
    }
}
