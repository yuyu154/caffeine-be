package com.woowacourse.caffeine.controller;

import com.woowacourse.caffeine.application.dto.OrderChangeRequest;
import com.woowacourse.caffeine.application.dto.OrderCreateRequest;
import com.woowacourse.caffeine.application.dto.OrderResponse;
import com.woowacourse.caffeine.dbunit.WebTestClientWithDbUnitTest;
import com.woowacourse.caffeine.domain.OrderStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

import static com.woowacourse.caffeine.controller.ShopController.V1_SHOP;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@WebTestClientWithDbUnitTest
public class OrderControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @DisplayName("주문 생성 및 단일 주문 조회2")
    void create2() {
        final long shopId = 100L;
        final String customerId = "";
        final long menuItemId1 = 1L;
        final long menuItemId2 = 2L;
        final List<Long> menuItems = Arrays.asList(menuItemId1, menuItemId2);
        final OrderCreateRequest orderCreateRequest = new OrderCreateRequest(customerId, menuItems);


        final String uri = webTestClient.post()
            .uri(String.format("%s/%d/orders/", "/v1/shops", shopId))
            .body(Mono.just(orderCreateRequest), OrderCreateRequest.class)
            .exchange().expectStatus().isCreated()
            .expectHeader().valueMatches("Location", V1_SHOP + "/\\d*/orders/\\d*")
            .expectBody()
            .returnResult()
            .getResponseHeaders().getLocation().toASCIIString();

        final EntityExchangeResult<OrderResponse> result = webTestClient.get()
            .uri(uri)
            .exchange()
            .expectStatus().isOk()
            .expectBody(OrderResponse.class)
            .returnResult();

        final OrderResponse orderResponse = result.getResponseBody();

        assertNotNull(orderResponse);
    }

    @Test
    @DisplayName("주문 생성 및 단일 주문 조회")
    void create() {
        final long shopId = 100L;
        final long menuItemId1 = 1L;
        final long menuItemId2 = 1L;
        final List<Long> menuItemIds = Arrays.asList(menuItemId1, menuItemId2);
        EntityExchangeResult<byte[]> result = createOrder(shopId, menuItemIds);

        String uri = result.getResponseHeaders().getLocation().toASCIIString();
        assertNotNull(uri);

        EntityExchangeResult<OrderResponse> getResult = webTestClient.get()
            .uri(uri)
            .exchange()
            .expectStatus().isOk()
            .expectBody(OrderResponse.class).returnResult();

        final OrderResponse orderResponse = getResult.getResponseBody();

        assertNotNull(orderResponse);
    }

    private EntityExchangeResult<byte[]> createOrder(final long shopId, final List<Long> menuItemIds) {
        final String url = String.format("%s/%d/orders", V1_SHOP, shopId);

        final OrderCreateRequest orderCreateRequest = new OrderCreateRequest("", menuItemIds);

        return webTestClient.post()
            .uri(url)
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(orderCreateRequest), OrderCreateRequest.class)
            .exchange()
            .expectStatus().isCreated()
            .expectHeader()
            .valueMatches("Location", V1_SHOP + "/\\d*/orders/\\d*")
            .expectBody().returnResult();
    }

    @Test
    @DisplayName("상태 별 주문 조회")
    void find_by_status() {
        final long shopId = 102L;
        assertThat(findOrdersByStatus(shopId, "pending")).hasSize(3);
        assertThat(findOrdersByStatus(shopId, "in_progress")).hasSize(3);
        assertThat(findOrdersByStatus(shopId, "finished")).hasSize(2);
    }

    private List<OrderResponse> findOrdersByStatus(final long shopId, final String status) {
        String url = String.format("%s/%d/orders?status=%s", V1_SHOP, shopId, status);
        EntityExchangeResult<List<OrderResponse>> result = webTestClient.get()
            .uri(url)
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(OrderResponse.class).returnResult();

        return result.getResponseBody();
    }

    @Test
    @DisplayName("주문 접수")
    void accept() {
        // given
        final long shopId = 100L;
        final long menuItemId1 = 1L;
        final long menuItemId2 = 1L;
        final List<Long> menuItemIds = Arrays.asList(menuItemId1, menuItemId2);
        String location = createOrder(shopId, menuItemIds)
            .getResponseHeaders().getLocation().toASCIIString();

        // when & then
        String url = String.format("%s/accept", location);
        webTestClient
            .put()
            .uri(url)
            .exchange()
            .expectStatus().isOk();
    }

    @Test
    @DisplayName("주문 거절")
    void reject() {
        // given
        final long shopId = 100L;
        final long menuItemId1 = 1L;
        final long menuItemId2 = 1L;
        final List<Long> menuItemIds = Arrays.asList(menuItemId1, menuItemId2);
        String location = createOrder(shopId, menuItemIds)
            .getResponseHeaders().getLocation().toASCIIString();

        // when & then
        String url = String.format("%s/reject", location);
        webTestClient
            .put()
            .uri(url)
            .exchange()
            .expectStatus().isOk();
    }

    @Test
    @DisplayName("주문 완료")
    void finish() {
        // given
        final long shopId = 100L;
        final long menuItemId1 = 1L;
        final long menuItemId2 = 1L;
        final List<Long> menuItemIds = Arrays.asList(menuItemId1, menuItemId2);
        String location = createOrder(shopId, menuItemIds)
            .getResponseHeaders().getLocation().toASCIIString();

        // when & then
        String acceptUrl = String.format("%s/accept", location);
        webTestClient
            .put()
            .uri(acceptUrl)
            .exchange()
            .expectStatus().isOk();
        String finishUrl = String.format("%s/finish", location);
        webTestClient
            .put()
            .uri(finishUrl)
            .exchange()
            .expectStatus().isOk();
    }

    @Test
    @DisplayName("PENDING -> IN_PROGRESS")
    void changePendingToProgress() {
        final long shopId = 100L;
        final long orderId = 1L;
        final OrderChangeRequest orderChangeRequest = new OrderChangeRequest(OrderStatus.IN_PROGRESS.toString());

        webTestClient.put()
            .uri(String.format("%s/%d/orders/%d/accept", V1_SHOP, shopId, orderId))
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(orderChangeRequest), OrderChangeRequest.class)
            .exchange()
            .expectStatus().isOk();
    }
}
