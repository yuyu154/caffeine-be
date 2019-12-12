package com.woowacourse.caffeine.controller;

import com.woowacourse.caffeine.application.dto.OrderChangeRequest;
import com.woowacourse.caffeine.application.dto.OrderCreateRequest;
import com.woowacourse.caffeine.application.dto.OrderResponse;
import com.woowacourse.caffeine.application.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

import static com.woowacourse.caffeine.controller.ShopController.V1_SHOP;

@RestController
@RequestMapping(OrderController.V1_ORDER)
public class OrderController {

    public static final String V1_ORDER = "/v1/shops/{shopId}/orders";

    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity createOrder(
            @PathVariable final long shopId,
            @RequestBody final OrderCreateRequest orderCreateRequest) {
        final OrderResponse orderResponse = orderService.create(shopId, orderCreateRequest);
        return ResponseEntity.created(URI.create(String.format("%s/%d/orders/%d", V1_SHOP, shopId, orderResponse.getId())))
            .build();
    }

    @GetMapping("/{orderId}")
    @Transactional
    public ResponseEntity findById(@PathVariable final Long orderId) {
        final OrderResponse orderResponse = orderService.findById(orderId);
        return ResponseEntity.ok(orderResponse);
    }

    @GetMapping
    public ResponseEntity findByStatus(@PathVariable final long shopId, @RequestParam("status") final String status) {
        final List<OrderResponse> orderResponses = orderService.findByStatus(shopId, status);
        return ResponseEntity.ok(orderResponses);
    }

    @PutMapping("/{orderId}/accept")
    public ResponseEntity acceptOrder(@PathVariable final long orderId) {
        orderService.acceptOrder(orderId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{orderId}/reject")
    public ResponseEntity rejectOrder(@PathVariable final long orderId) {
        orderService.rejectOrder(orderId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{orderId}/finish")
    public ResponseEntity finishOrder(@PathVariable final long orderId) {
        orderService.finishOrder(orderId);
        return ResponseEntity.ok().build();
    }
}
