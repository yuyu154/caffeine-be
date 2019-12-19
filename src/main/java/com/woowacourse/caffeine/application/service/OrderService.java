package com.woowacourse.caffeine.application.service;

import com.woowacourse.caffeine.application.converter.MenuItemConverter;
import com.woowacourse.caffeine.application.dto.MenuItemResponse;
import com.woowacourse.caffeine.application.dto.OrderCreateRequest;
import com.woowacourse.caffeine.application.dto.OrderResponse;
import com.woowacourse.caffeine.domain.MenuItem;
import com.woowacourse.caffeine.domain.Order;
import com.woowacourse.caffeine.domain.OrderStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class OrderService {

    private final OrderInternalService orderInternalService;
    private final OrderItemInternalService orderItemInternalService;

    public OrderService(final OrderInternalService orderInternalService,
                        final OrderItemInternalService orderItemInternalService) {
        this.orderInternalService = orderInternalService;
        this.orderItemInternalService = orderItemInternalService;
    }

    public long create(final long shopId, final OrderCreateRequest orderCreateRequest, final String customerId) {

        final Order order = orderInternalService.create(shopId, orderCreateRequest, customerId);
        return order.getId();
    }

    @Transactional(readOnly = true)
    public OrderResponse findById(final Long orderId) {
        final Order order = orderInternalService.findById(orderId);
        final List<MenuItem> menuItems = orderItemInternalService.findMenusByOrder(order);
        final List<MenuItemResponse> menuItemResponses = convertToMenuItemResponses(menuItems);
        return new OrderResponse(order.getId(), order.getOrderStatus().name(), menuItemResponses);
    }

    private List<MenuItemResponse> convertToMenuItemResponses(final List<MenuItem> menuItems) {
        return menuItems.stream()
            .map(MenuItemConverter::convertToResponse)
            .collect(toList());
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> findByStatus(final long shopId, final String orderStatusName) {
        final List<Order> orders = orderInternalService.findByStatus(shopId, OrderStatus.from(orderStatusName));
        return convertToOrderResponses(orders);
    }

    private List<OrderResponse> convertToOrderResponses(final List<Order> orders) {
        return orders.stream()
            .map(order -> new OrderResponse(
                order.getId(),
                order.getOrderStatus().name(),
                convertToMenuItemResponses(orderItemInternalService.findMenusByOrder(order))
            ))
            .collect(toList());
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
