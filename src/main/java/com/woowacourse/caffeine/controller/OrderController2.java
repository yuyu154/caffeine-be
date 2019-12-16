package com.woowacourse.caffeine.controller;

import com.woowacourse.caffeine.application.dto.OrderCreateRequest;
import com.woowacourse.caffeine.application.dto.OrderResponse;
import com.woowacourse.caffeine.application.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;

import static com.woowacourse.caffeine.controller.ShopController.V1_SHOP;

@Controller
@RequestMapping(OrderController2.V2_ORDER)
public class OrderController2 {

    public static final String V2_ORDER = "/v2/shops/{shopId}/orders";

    private final OrderService orderService;

    public OrderController2(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping()
    public ResponseEntity createOrder(
        @PathVariable final long shopId,
        @RequestBody final OrderCreateRequest orderCreateRequest) {

        final OrderResponse orderResponse = orderService.create2(shopId, orderCreateRequest);
        return ResponseEntity.created(URI.create(String.format("%s/%d/orders/%d", V1_SHOP, shopId, orderResponse.getId())))
            .build();
    }
}
