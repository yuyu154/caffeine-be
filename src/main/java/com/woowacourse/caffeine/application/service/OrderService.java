package com.woowacourse.caffeine.application.service;

import com.woowacourse.caffeine.application.dto.OrderResponse;
import com.woowacourse.caffeine.domain.MenuItem;
import com.woowacourse.caffeine.domain.Order;
import com.woowacourse.caffeine.domain.OrderStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final OrderInternalService orderInternalService;
    private final ShopNotificationService shopNotificationService;

    public OrderService(final OrderInternalService orderInternalService, final ShopNotificationService shopNotificationService) {
        this.orderInternalService = orderInternalService;
        this.shopNotificationService = shopNotificationService;
    }

    public OrderResponse create(final MenuItem menuItem) {
        final Order order = orderInternalService.create(menuItem);
        return new OrderResponse(order.getId(), 1L, order.getMenuItem().getId());
    }

    @Transactional(readOnly = true)
    public OrderResponse findOrderById(final Long orderId, final long customerId) {
        //TODO Session
        final Order order = orderInternalService.findById(orderId);
        return new OrderResponse(order.getId(), customerId, order.getMenuItem().getId());
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> findAllOrders(final long customerId, final String statusValue) {
        logger.debug("Method is {}", statusValue);
        final List<Order> orders = orderInternalService.findAllByOrderStatus(statusValue);

        final List<OrderResponse> orderResponses = orders.stream()
            .map(order -> new OrderResponse(order.getId(), customerId, order.getMenuItem().getId()))
            .collect(Collectors.toList());
        return orderResponses;
    }

    public void changeStatus(final Long orderId, final Long customerId, final String status) {
        final Order order = orderInternalService.findById(orderId);
        final Long shopId = order.getMenuItem().getVendor().getId();

        try {
            order.changeStatus(OrderStatus.valueOf(status));
            final String message = "주문이 접수되었습니다";
            shopNotificationService.send(shopId, message);
        } catch (IllegalArgumentException e) {
            logger.error("{}", e.getMessage());
            final String message = "주문이 접수되지 않았습니다";
            shopNotificationService.send(shopId, message);
        }
    }
}
