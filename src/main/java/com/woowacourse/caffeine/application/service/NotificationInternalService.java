package com.woowacourse.caffeine.application.service;

import com.woowacourse.caffeine.application.dto.NotificationSendRequest;
import com.woowacourse.caffeine.application.dto.NotificationSubscriptionsRequest;

public interface NotificationInternalService {
    void subscribeShop(final long shopId, final NotificationSubscriptionsRequest request);
    void subscribeCustomer(final String customerId, final NotificationSubscriptionsRequest request);
    void sendShop(final long shopId, final NotificationSendRequest request);
    void sendCustomer(final String customerId, final NotificationSendRequest request);
}
