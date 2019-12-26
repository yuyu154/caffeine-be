package com.woowacourse.caffeine.application.service;

import com.woowacourse.caffeine.application.dto.NotificationSendRequest;
import com.woowacourse.caffeine.application.dto.NotificationSubscriptionsRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
class FcmNotificationInternalService implements NotificationInternalService {

    private static final Logger logger = LoggerFactory.getLogger(FcmNotificationInternalService.class);

    private final ShopInternalService shopInternalService;
    private final WebClient notificationApiClient;

    public FcmNotificationInternalService(final ShopInternalService shopInternalService) {
        this.shopInternalService = shopInternalService;
        this.notificationApiClient = WebClient.builder()
            .baseUrl("http://notification:8000")
            .build();
    }

    public void subscribeShop(final long shopId, final NotificationSubscriptionsRequest request) {
        shopInternalService.findById(shopId);
        subscribe(String.format("/shops/%s/subscribe", shopId), request);
    }

    private void subscribe(final String url, final NotificationSubscriptionsRequest request) {
        ResponseEntity<byte[]> res = notificationApiClient.post()
            .uri(url)
            .body(Mono.just(request), NotificationSubscriptionsRequest.class)
            .retrieve()
            .toEntity(byte[].class)
            .block();
        if (res.getStatusCode() != HttpStatus.OK) {
            logger.info("Subscription failed with url '{}' and token '{}'", url, request.getToken());
        }
    }

    public void subscribeCustomer(final String customerId, final NotificationSubscriptionsRequest request) {
        subscribe(String.format("/customers/%s/subscribe", customerId), request);
    }

    public void sendShop(final long shopId, final NotificationSendRequest request) {
        send(String.format("/shops/%s/send", shopId), request);
    }

    private void send(final String url, final NotificationSendRequest request) {
        ResponseEntity<byte[]> res = notificationApiClient.post()
            .uri(url)
            .body(Mono.just(request), NotificationSendRequest.class)
            .retrieve()
            .toEntity(byte[].class)
            .block();
        if (res.getStatusCode() != HttpStatus.OK) {
            logger.info("Failed to send with url '{}'", url);
        }
    }

    public void sendCustomer(final String customerId, final NotificationSendRequest request) {
        send(String.format("/shops/%s/send", customerId), request);
    }
}
