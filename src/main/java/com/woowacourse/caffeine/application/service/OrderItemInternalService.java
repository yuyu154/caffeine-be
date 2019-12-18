package com.woowacourse.caffeine.application.service;

import com.woowacourse.caffeine.domain.MenuItem;
import com.woowacourse.caffeine.domain.Order;
import com.woowacourse.caffeine.domain.OrderItem;
import com.woowacourse.caffeine.repository.OrderItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemInternalService {

    private OrderItemRepository orderItemRepository;

    public OrderItemInternalService(final OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public OrderItem save(final OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    public List<MenuItem> findMenusByOrder(final Order order) {
        return orderItemRepository.findAllMenuItemByOrder(order);
    }
}
