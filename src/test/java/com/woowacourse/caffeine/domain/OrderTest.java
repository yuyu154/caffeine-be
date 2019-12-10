package com.woowacourse.caffeine.domain;

import com.woowacourse.caffeine.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class OrderTest {

    @Autowired
    OrderRepository orderRepository;

    @Test
    public void BaseTimeEntity_등록() {
        //given
        LocalDateTime now = LocalDateTime.of(2019, 12, 6, 0, 0, 0);
        orderRepository.save(new Order());

        //when
        List<Order> orders = orderRepository.findAll();

        //then
        Order order = orders.get(0);

        assertThat(order.getCreatedDate()).isAfter(now);
        assertThat(order.getModifiedDate()).isAfter(now);
    }
}
