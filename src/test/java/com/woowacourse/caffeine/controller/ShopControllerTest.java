package com.woowacourse.caffeine.controller;

import com.woowacourse.caffeine.application.dto.ShopCreateRequest;
import com.woowacourse.caffeine.application.dto.ShopResponse;
import com.woowacourse.caffeine.application.dto.ShopResponses;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;

import static com.woowacourse.caffeine.controller.ShopController.V1_SHOP;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

    @Test
    @DisplayName("상점 목록 조회")
    void findAllShops() {
        //given
        ShopResponse shopResponse1 = new ShopResponse(100, "어디야 커피 잠실점");
        ShopResponse shopResponse2 = new ShopResponse(101, "어디야 커피 잠실점2");
        ShopResponses shopResponses = new ShopResponses();
        shopResponses.setShopResponses(Arrays.asList(shopResponse1, shopResponse2));

        //when & then
        final ShopResponses actual = webTestClient.get()
            .uri(String.format("%s/", V1_SHOP))
            .exchange()
            .expectStatus().isOk()
            .expectBody(ShopResponses.class)
            .returnResult()
            .getResponseBody();

        assertNotNull(actual);
        assertThat(actual.getShopResponses().get(0).getId()).isEqualTo(shopResponse1.getId());
        assertThat(actual.getShopResponses().get(1).getId()).isEqualTo(shopResponse2.getId());
    }
}
