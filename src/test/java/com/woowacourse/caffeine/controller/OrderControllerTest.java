package com.woowacourse.caffeine.controller;

import com.woowacourse.caffeine.application.dto.OrderChangeRequest;
import com.woowacourse.caffeine.application.dto.OrderCreateRequest;
import com.woowacourse.caffeine.application.dto.OrderResponse;
import com.woowacourse.caffeine.domain.OrderStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.woowacourse.caffeine.controller.OrderController.V1_ORDER;
import static com.woowacourse.caffeine.controller.ShopController.V1_SHOP;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class OrderControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @DisplayName("주문 생성 및 단일 주문 조회")
    void create() {
        final long shopId = 100L;
        final long menuItemId = 987654321L;
        EntityExchangeResult<byte[]> result = createOrder(shopId, menuItemId);

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

    private EntityExchangeResult<byte[]> createOrder(final long shopId, final long menuItemId) {
        final String url = String.format("%s/%d/orders", V1_SHOP, shopId);

        final OrderCreateRequest orderCreateRequest = new OrderCreateRequest(menuItemId, "");

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
        final long menuItemId = 987654321L;
        String location = createOrder(shopId, menuItemId)
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
        final long menuItemId = 987654321L;
        String location = createOrder(shopId, menuItemId)
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
        final long menuItemId = 987654321L;
        String location = createOrder(shopId, menuItemId)
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

//    @Test
    @DisplayName("PENDING -> IN_PROGRESS")
    void changePendingToProgress() {
        final long orderId = 987654317L;
        final OrderChangeRequest orderChangeRequest = new OrderChangeRequest(OrderStatus.IN_PROGRESS.toString());

        webTestClient.put()
            .uri(V1_ORDER + "/" + orderId)
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(orderChangeRequest), OrderChangeRequest.class)
            .exchange()
            .expectStatus().isOk();
    }
}
