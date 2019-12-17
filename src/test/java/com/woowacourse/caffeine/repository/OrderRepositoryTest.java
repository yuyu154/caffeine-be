package com.woowacourse.caffeine.repository;

import com.woowacourse.caffeine.domain.Order;
import com.woowacourse.caffeine.domain.OrderStatus;
import com.woowacourse.caffeine.dbunit.DbUnitTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DbUnitTest
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Test
    void findByStatusTest() {
        List<Order> orders = orderRepository.findByShopAndOrderStatus(shopRepository.findById(102L).get(), OrderStatus.PENDING);

        assertThat(orders).hasSize(3);
    }
}