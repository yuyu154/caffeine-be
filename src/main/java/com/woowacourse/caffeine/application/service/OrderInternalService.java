package com.woowacourse.caffeine.application.service;

import com.woowacourse.caffeine.application.dto.OrderCreateRequest;
import com.woowacourse.caffeine.application.exception.OrderNotFoundException;
import com.woowacourse.caffeine.domain.MenuItem;
import com.woowacourse.caffeine.domain.Order;
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

    private final OrderRepository orderRepository;

    public OrderInternalService(final ShopInternalService shopInternalService,
                                final MenuItemInternalService menuItemInternalService,
                                final ShopNotificationService shopNotificationService,
                                final CustomerNotificationService customerNotificationService,
                                final OrderRepository orderRepository) {
        this.shopInternalService = shopInternalService;
        this.menuItemInternalService = menuItemInternalService;
        this.shopNotificationService = shopNotificationService;
        this.customerNotificationService = customerNotificationService;
        this.orderRepository = orderRepository;
    }

    public Order create(final long shopId, final OrderCreateRequest request) {
        final Shop shop = shopInternalService.findById(shopId);
        final MenuItem menuItem = menuItemInternalService.findById(request.getMenuItemId());
        shopNotificationService.send(shopId, "주문이 들어왔습니다.");
        return orderRepository.save(Order.createOrder(shop, menuItem, request.getCustomerId()));
    }

    @Transactional(readOnly = true)
    public Order findById(final Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
    }

    @Transactional(readOnly = true)
    public List<Order> findByStatus(final Shop shop, final OrderStatus status) {
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
