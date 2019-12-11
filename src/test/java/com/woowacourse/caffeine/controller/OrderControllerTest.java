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

import static com.woowacourse.caffeine.controller.OrderController.V1_ORDERS;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class OrderControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @DisplayName("주문 생성 및 단일 주문 조회")
    void createOrder() {
        final String postUri = V1_ORDERS;
        final long menuItemId = 987654321L;

        final OrderCreateRequest orderCreateRequest = new OrderCreateRequest(menuItemId);

        EntityExchangeResult<byte[]> result = webTestClient.post()
            .uri(postUri)
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(orderCreateRequest), OrderCreateRequest.class)
            .exchange()
            .expectStatus().isCreated()
            .expectHeader()
            .valueMatches("Location", postUri + "/\\d*")
            .expectBody().returnResult();

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

    @Test
    @DisplayName("Pending 주문 조회")
    void getPendingOrders() {
        EntityExchangeResult<List<OrderResponse>> result = webTestClient.get()
            .uri(V1_ORDERS + "/?status=PENDING")
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(OrderResponse.class).returnResult();

        List<OrderResponse> orderResponses = result.getResponseBody();
        assertNotNull(orderResponses);
    }

    @Test
    @DisplayName("InProgress 주문 조회")
    void getInProgressOrders() {
        EntityExchangeResult<List<OrderResponse>> result = webTestClient.get()
            .uri(V1_ORDERS + "/?status=IN_PROGRESS")
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(OrderResponse.class).returnResult();

        List<OrderResponse> orderResponses = result.getResponseBody();
        assertNotNull(orderResponses);
    }

    @Test
    @DisplayName("Finished 주문 조회")
    void getFinishedOrders() {
        EntityExchangeResult<List<OrderResponse>> result = webTestClient.get()
            .uri(V1_ORDERS + "/?status=FINISHED")
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(OrderResponse.class).returnResult();

        List<OrderResponse> orderResponses = result.getResponseBody();
        assertNotNull(orderResponses);
    }

    @Test
    @DisplayName("PENDING -> IN_PROGRESS")
    void changePendingToProgress() {
        final long orderId = 987654317L;
        final OrderChangeRequest orderChangeRequest = new OrderChangeRequest(OrderStatus.IN_PROGRESS.toString());

        webTestClient.put()
            .uri(V1_ORDERS + "/" + orderId)
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(orderChangeRequest), OrderChangeRequest.class)
            .exchange()
            .expectStatus().isOk();
    }

    @Test
    @DisplayName("IN_PROGRESS -> FINISHED")
    void changeProgressToFinished() {
        final long orderId = 987654318L;
        final OrderChangeRequest orderChangeRequest = new OrderChangeRequest(OrderStatus.FINISHED.toString());

        webTestClient.put()
            .uri(V1_ORDERS + "/" + orderId)
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(orderChangeRequest), OrderChangeRequest.class)
            .exchange()
            .expectStatus().isOk();
    }
}
