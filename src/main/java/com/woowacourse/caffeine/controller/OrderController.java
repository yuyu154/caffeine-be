package com.woowacourse.caffeine.controller;

import com.woowacourse.caffeine.application.dto.OrderChangeRequest;
import com.woowacourse.caffeine.application.dto.OrderCreateRequest;
import com.woowacourse.caffeine.application.dto.OrderResponse;
import com.woowacourse.caffeine.application.service.MenuItemInternalService;
import com.woowacourse.caffeine.application.service.OrderService;
import com.woowacourse.caffeine.domain.MenuItem;
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

@RestController
@RequestMapping(OrderController.V1_ORDERS)
public class OrderController {

    public static final String V1_ORDERS = "/v1/orders";
    private static final Long CUSTOMER_ID = 1L;

    private final OrderService orderService;
    private final MenuItemInternalService menuItemInternalService;

    public OrderController(final OrderService orderService, final MenuItemInternalService menuItemInternalService) {
        this.orderService = orderService;
        this.menuItemInternalService = menuItemInternalService;
    }

    @PostMapping
    public ResponseEntity createOrder(@RequestBody final OrderCreateRequest orderCreateRequest) {
        final long menuItemId = orderCreateRequest.getMenuItemId();
        final MenuItem menuItem = menuItemInternalService.findByMenuItemId(menuItemId);
        final OrderResponse orderResponse = orderService.create(menuItem);
        return ResponseEntity.created(URI.create(String.format("%s%s%d", V1_ORDERS, "/", orderResponse.getOrderId())))
            .build();
    }

    @GetMapping("/{orderId}")
    @Transactional
    public ResponseEntity findByOrderId(@PathVariable final Long orderId) {
        final long customerId = CUSTOMER_ID;
        final OrderResponse orderResponse = orderService.findOrderById(orderId, customerId);
        return ResponseEntity.ok(orderResponse);
    }

    @GetMapping
    public ResponseEntity findByOrdersByStatus(@RequestParam("status") final String status) {
        final long customerId = CUSTOMER_ID;
        final List<OrderResponse> orderResponses = orderService.findAllOrders(customerId, status);

        return ResponseEntity.ok(orderResponses);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity changeStatus(
        @PathVariable("orderId") final Long orderId,
        @RequestBody final OrderChangeRequest orderChangeRequest) {

        orderService.changeStatus(
            orderId,
            CUSTOMER_ID,
            orderChangeRequest.getChangeStatus());

        return ResponseEntity.ok("CHANGE SUCCESS");
    }
}

