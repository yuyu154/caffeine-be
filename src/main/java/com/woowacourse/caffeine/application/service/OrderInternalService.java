package com.woowacourse.caffeine.application.service;

import com.woowacourse.caffeine.application.exception.OrderNotFoundException;
import com.woowacourse.caffeine.domain.MenuItem;
import com.woowacourse.caffeine.domain.Order;
import com.woowacourse.caffeine.domain.OrderStatus;
import com.woowacourse.caffeine.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderInternalService {

    private final OrderRepository orderRepository;

    public OrderInternalService(final OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order create(final MenuItem menuItem) {
        final Order order = orderRepository.save(Order.createOrder(menuItem));
        return order;
    }

    @Transactional(readOnly = true)
    public Order findById(final Long orderId) {
        final Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new OrderNotFoundException(orderId));

        return order;
    }

    @Transactional(readOnly = true)
    public List<Order> findAllByOrderStatus(final String status) {
        final OrderStatus orderStatus = OrderStatus.valueOf(status);

        return orderRepository.findAllByOrderStatus(orderStatus);
    }
}
