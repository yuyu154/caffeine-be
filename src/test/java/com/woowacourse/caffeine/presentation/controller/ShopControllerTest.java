package com.woowacourse.caffeine.presentation.controller;

import com.woowacourse.caffeine.application.dto.ShopCreateRequest;
import com.woowacourse.caffeine.application.dto.ShopResponse;
import com.woowacourse.caffeine.application.dto.ShopResponses;
import com.woowacourse.caffeine.dbunit.WebTestClientWithDbUnitTest;
import com.woowacourse.caffeine.mock.ShopResponseRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static com.woowacourse.caffeine.presentation.controller.ShopController.V1_SHOP;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@WebTestClientWithDbUnitTest
public class ShopControllerTest {

    private static final long DEFAULT_SHOP_ID = 100L;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @DisplayName("상점 생성")
    void create_shop() {
        // given
        ShopResponse shopResponse = ShopResponseRepository.shopResponse1;
        ShopCreateRequest shopCreateRequest =
            new ShopCreateRequest(
                shopResponse.getName(),
                shopResponse.getImage(),
                shopResponse.getAddress(),
                shopResponse.getPhoneNumber());

        // when
        EntityExchangeResult<byte[]> response = webTestClient.post()
            .uri(V1_SHOP)
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(shopCreateRequest), ShopCreateRequest.class)
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
            .jsonPath("$.name").isEqualTo(shopResponse.getName())
            .jsonPath("$.image").isEqualTo(shopResponse.getImage())
            .jsonPath("$.address").isEqualTo(shopResponse.getAddress())
            .jsonPath("$.phoneNumber").isEqualTo(shopResponse.getPhoneNumber());
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
        ShopResponse shopResponse1 = ShopResponseRepository.shopResponse1;
        ShopResponse shopResponse2 = ShopResponseRepository.shopResponse2;

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

    @Test
    @DisplayName("주소로 검색을 했을 때 잘 찾는지")
    void search_by_address() {

        webTestClient.get()
            .uri(V1_SHOP + "/search/?keyword=address&contents=오금로" +
                "&size=5&page=0")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
            .jsonPath("$.content").isArray();

    }

    @Test
    @DisplayName("제목으로 검색 했을 때 잘 찾는 지")
    void search_by_name() {
        webTestClient.get()
            .uri(V1_SHOP + "/search/?keyword=name&contents=송파" +
                "&size=5&page=0")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
            .jsonPath("$.content").isArray();
    }

    @Test
    @DisplayName("올바르지 않은 검색 요청")
    void invalid_search_request() {
        webTestClient.get()
            .uri(V1_SHOP + "/search/?keyword=add&contents=송파")
            .exchange()
            .expectStatus()
            .isBadRequest();
    }

    @Test
    @DisplayName("올바르지 않은 키워드 요청")
    void invalid_keyword_search_request() {
        webTestClient.get()
            .uri(V1_SHOP + "/search/?key=name&contents=송파")
            .exchange()
            .expectStatus()
            .isBadRequest();
    }
}
