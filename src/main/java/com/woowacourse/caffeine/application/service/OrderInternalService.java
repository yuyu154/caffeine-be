package com.woowacourse.caffeine.application.service;

import com.woowacourse.caffeine.application.dto.OrderCreateRequest;
import com.woowacourse.caffeine.application.exception.OrderNotFoundException;
import com.woowacourse.caffeine.domain.MenuItem;
import com.woowacourse.caffeine.domain.Order;
import com.woowacourse.caffeine.domain.OrderItem;
import com.woowacourse.caffeine.domain.OrderStatus;
import com.woowacourse.caffeine.domain.Shop;
import com.woowacourse.caffeine.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
class OrderInternalService {

    private final ShopInternalService shopInternalService;
    private final MenuItemInternalService menuItemInternalService;
    private final ShopNotificationService shopNotificationService;
    private final CustomerNotificationService customerNotificationService;
    private final OrderItemInternalService orderItemInternalService;

    private final OrderRepository orderRepository;

    public OrderInternalService(final ShopInternalService shopInternalService,
                                final MenuItemInternalService menuItemInternalService,
                                final ShopNotificationService shopNotificationService,
                                final CustomerNotificationService customerNotificationService,
                                final OrderItemInternalService orderItemInternalService,
                                final OrderRepository orderRepository) {
        this.shopInternalService = shopInternalService;
        this.menuItemInternalService = menuItemInternalService;
        this.shopNotificationService = shopNotificationService;
        this.customerNotificationService = customerNotificationService;
        this.orderItemInternalService = orderItemInternalService;
        this.orderRepository = orderRepository;
    }

    public Order create(final long shopId, final OrderCreateRequest orderCreateRequest, final String customerId) {
        final Shop shop = shopInternalService.findById(shopId);
        final Order order = orderRepository.save(Order.createOrder(shop, customerId));
        final List<Long> menuItemsNumber = orderCreateRequest.getMenuItemIds();
        for (final Long menuItemId : menuItemsNumber) {
            final MenuItem menuItem = menuItemInternalService.findById(menuItemId);
            final OrderItem orderItem = OrderItem.createOrderItem(order, menuItem);
            orderItemInternalService.save(orderItem);
        }
        shopNotificationService.send(shopId, "주문이 들어왔습니다");
        return order;
    }

    @Transactional(readOnly = true)
    public Order findById(final Long orderId) {
        return orderRepository.findById(orderId)
            .orElseThrow(() -> new OrderNotFoundException(orderId));
    }

    @Transactional(readOnly = true)
    public List<Order> findByStatus(final long shopId, final OrderStatus status) {
        final Shop shop = shopInternalService.findById(shopId);
        return orderRepository.findByShopAndOrderStatus(shop, status);
    }

    public void acceptOrder(final Order order) {
        order.accept();
        customerNotificationService.send(order.getCustomerId(), "주문이 접수됐습니다.");
    }

    public void rejectOrder(final Order order) {
        order.reject();
        customerNotificationService.send(order.getCustomerId(), "주문이 거절됐습니다.");
    }

    public void finishOrder(final Order order) {
        order.finish();
        customerNotificationService.send(order.getCustomerId(), "음료가 나왔습니다. 찾아가세요~");
    }
}
