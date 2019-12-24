package com.woowacourse.caffeine.presentation.controller;

import com.woowacourse.caffeine.application.dto.NotificationSubscriptionsRequest;
import com.woowacourse.caffeine.application.exception.ShopNotFoundException;
import com.woowacourse.caffeine.application.service.NotificationInternalService;
import com.woowacourse.caffeine.dbunit.WebTestClientWithDbUnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static com.woowacourse.caffeine.presentation.controller.SubscribeController.V1_SUBSCRIBE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;

@WebTestClientWithDbUnitTest
@ExtendWith(MockitoExtension.class)
public class SubscribeControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private NotificationInternalService notificationInternalService;

    @Test
    @DisplayName("사용자 구독 요청")
    void subscribe_customer() {
        NotificationSubscriptionsRequest request = new NotificationSubscriptionsRequest("abcdefg");
        webTestClient.post()
            .uri(V1_SUBSCRIBE + "/customers")
            .body(Mono.just(request), NotificationSubscriptionsRequest.class)
            .exchange()
            .expectStatus().isOk();
    }

    @Test
    void owner_subscribe() {
        NotificationSubscriptionsRequest request = new NotificationSubscriptionsRequest("abcdefg");
        webTestClient.post()
            .uri("/v1/subscribe/shops/100")
            .body(Mono.just(request), NotificationSubscriptionsRequest.class)
            .exchange()
            .expectStatus().isOk();
    }

    @Test
    @DisplayName("등록되지 않은 매장 사장이 구독 요청할 때 에러")
    void un_register_owner_subscribe() {
        // given
        doThrow(new ShopNotFoundException(1L)).when(notificationInternalService).subscribeShop(eq(1L), any());

        NotificationSubscriptionsRequest request = new NotificationSubscriptionsRequest("abcdefg");
        webTestClient.post()
            .uri("/v1/subscribe/shops/1")
            .body(Mono.just(request), NotificationSubscriptionsRequest.class)
            .exchange()
            .expectStatus().isBadRequest();
    }
}
