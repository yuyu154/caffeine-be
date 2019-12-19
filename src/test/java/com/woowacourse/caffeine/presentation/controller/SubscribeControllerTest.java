package com.woowacourse.caffeine.presentation.controller;

import com.woowacourse.caffeine.dbunit.WebTestClientWithDbUnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.woowacourse.caffeine.presentation.controller.SubscribeController.V1_SUBSCRIBE;

@WebTestClientWithDbUnitTest
public class SubscribeControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @DisplayName("사용자 구독 요청")
    void subscribe_customer() {
        webTestClient.get()
            .uri(V1_SUBSCRIBE + "/customers")
            .exchange()
            .expectStatus().isOk();
    }

    @Test
    void owner_subscribe() {
        webTestClient.get()
            .uri("/v1/subscribe/shops/100")
            .exchange()
            .expectStatus().isOk();
    }

    @Test
    @DisplayName("등록되지 않은 매장 사장이 구독 요청할 때 에러")
    void un_register_owner_subscribe() {
        webTestClient.get()
            .uri("v1/subscribe/shops/1")
            .exchange()
            .expectStatus().isBadRequest();
    }
}
