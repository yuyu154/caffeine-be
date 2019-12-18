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

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class OrderService {

    private final ShopInternalService shopInternalService;
    private final OrderInternalService orderInternalService;
    private final OrderItemInternalService orderItemInternalService;

    public OrderService(final ShopInternalService shopInternalService,
                        final OrderInternalService orderInternalService,
                        final OrderItemInternalService orderItemInternalService) {
        this.orderInternalService = orderInternalService;
        this.shopInternalService = shopInternalService;
        this.orderItemInternalService = orderItemInternalService;
    }

    public long create(final long shopId, final OrderCreateRequest orderCreateRequest) {
        final Order order = orderInternalService.create(shopId, orderCreateRequest);
        return order.getId();
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
        final List<MenuItem> menuItems = orderItemInternalService.findMenusByOrder(order);
        final List<MenuItemResponse> menuItemResponses = convertToMenuItemResponses(menuItems);
        return new OrderResponse(order.getId(), order.getOrderStatus().name(), menuItemResponses);
    }

    private List<MenuItemResponse> convertToMenuItemResponses(final List<MenuItem> menuItems) {
        return menuItems.stream()
            .map(this::convertToMenuItemResponse)
            .collect(toList());
    }

    // orderInternalService.findByStatus(shopId, OrderStatus.from(orderStatusName) 고려
    @Transactional(readOnly = true)
    public List<OrderResponse> findByStatus(final long shopId, final String orderStatusName) {
        final Shop shop = shopInternalService.findById(shopId);
        final List<Order> orders = orderInternalService.findByStatus(shop, OrderStatus.from(orderStatusName));
        final List<OrderResponse> results = new ArrayList<>();

        for (final Order order : orders) {
            final List<MenuItem> menuItems = orderItemInternalService.findMenusByOrder(order);
            final OrderResponse orderResponse = new OrderResponse(
                order.getId(),
                order.getOrderStatus().name(),
                convertToMenuItemResponses(menuItems)
            );
            results.add(orderResponse);
        }

        return results;
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
