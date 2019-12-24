package com.woowacourse.caffeine.application.service;

import com.woowacourse.caffeine.application.dto.NotificationSubscriptionsRequest;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final NotificationInternalService notificationInternalService;

    public NotificationService(final NotificationInternalService notificationInternalService) {
        this.notificationInternalService = notificationInternalService;
    }

    public void subscribeShop(final long shopId, final NotificationSubscriptionsRequest request) {
        notificationInternalService.subscribeShop(shopId, request);
    }

    public void subscribeCustomer(final String customerId, final NotificationSubscriptionsRequest request) {
        notificationInternalService.subscribeCustomer(customerId, request);
    }
}
