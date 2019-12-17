package com.woowacourse.caffeine.application.service;

import com.woowacourse.caffeine.application.dto.MenuItemResponse;
import com.woowacourse.caffeine.application.dto.OrderCreateRequest;
import com.woowacourse.caffeine.application.dto.OrderResponse;
import com.woowacourse.caffeine.domain.MenuItem;
import com.woowacourse.caffeine.domain.Order;
import com.woowacourse.caffeine.domain.OrderStatus;
import com.woowacourse.caffeine.domain.Shop;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderInternalService orderInternalService;
    private final ShopInternalService shopInternalService;

    public OrderService(final OrderInternalService orderInternalService,
                        final ShopInternalService shopInternalService) {
        this.orderInternalService = orderInternalService;
        this.shopInternalService = shopInternalService;
    }

    public OrderResponse create(final long shopId, final OrderCreateRequest request) {
        final Order order = orderInternalService.create(shopId, request);
        return new OrderResponse(order.getId(), order.getOrderStatus().name(), Collections.singletonList(convertToMenuItemResponse(order.getMenuItem())));
    }

    private MenuItemResponse convertToMenuItemResponse(final MenuItem menuItem) {
        return new MenuItemResponse(
            menuItem.getId(),
            menuItem.getName(),
            menuItem.getNameInEnglish(),
            menuItem.getDescription(),
            menuItem.getPrice(),
            menuItem.getImgUrl(),
            menuItem.getCategory());
    }

    @Transactional(readOnly = true)
    public OrderResponse findById(final Long orderId) {
        final Order order = orderInternalService.findById(orderId);
        return new OrderResponse(order.getId(), order.getOrderStatus().name(), Collections.singletonList(convertToMenuItemResponse(order.getMenuItem())));
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> findByStatus(final long shopId, final String orderStatusName) {
        final Shop shop = shopInternalService.findById(shopId);
        final List<Order> orders = orderInternalService.findByStatus(shop, OrderStatus.from(orderStatusName));

        return orders.stream()
            .map(order -> new OrderResponse(order.getId(), order.getOrderStatus().name(), Collections.singletonList(convertToMenuItemResponse(order.getMenuItem()))))
            .collect(Collectors.toList());
    }

    public void acceptOrder(final long orderId) {
        Order order = orderInternalService.findById(orderId);
        orderInternalService.acceptOrder(order);
    }

    public void rejectOrder(final long orderId) {
        Order order = orderInternalService.findById(orderId);
        orderInternalService.rejectOrder(order);
    }

    public void finishOrder(final long orderId) {
        Order order = orderInternalService.findById(orderId);
        orderInternalService.finishOrder(order);
    }
}
