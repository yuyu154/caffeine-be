package com.woowacourse.caffeine.controller;

import com.woowacourse.caffeine.application.dto.ShopCreateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static com.woowacourse.caffeine.controller.ShopController.V1_SHOP;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class ShopControllerTest {

    private static final long DEFAULT_SHOP_ID = 100L;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @DisplayName("상점 생성")
    void create_shop() {
        // given
        String name = "어디야 커피";
        ShopCreateRequest request = new ShopCreateRequest(name);

        // when
        EntityExchangeResult<byte[]> response = webTestClient.post()
            .uri(V1_SHOP)
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(request), ShopCreateRequest.class)
            .exchange()
            .expectStatus().isCreated()
            .expectHeader()
            .valueMatches("Location", V1_SHOP + "/\\d*")
            .expectBody().returnResult();

        // then
        webTestClient.get()
            .uri(response.getResponseHeaders().getLocation().toASCIIString())
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.id").isNotEmpty()
            .jsonPath("$.name").isEqualTo(name);
    }

    @Test
    @DisplayName("상점 별 메뉴 목록 조회")
    void menus_by_shop() {
        // when & then
        webTestClient.get()
            .uri(String.format("%s/%d/menus", V1_SHOP, DEFAULT_SHOP_ID))
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$").isArray();
    }
}
