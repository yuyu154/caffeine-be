package com.woowacourse.caffeine.domain;

import com.woowacourse.caffeine.domain.exception.InvalidOrderStatusChangeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderTest {

    private MenuItem menuItem;
    private Order order;

    @BeforeEach
    void setUp() {
        Shop shop = new Shop("어디야 커피");
        menuItem = new MenuItem("아메리카노", "Americano", "구수한 아메리카노", 2500, "", "coffee", shop);
        order = Order.createOrder(shop, menuItem, "");
    }

   @Test
    @DisplayName("주문 승인")
    void accept() {
        order.accept();
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.IN_PROGRESS);
    }

    @Test
    @DisplayName("올바르지 않은 주문 승인")
    void invalid_accept() {
        order.accept();
        assertThrows(InvalidOrderStatusChangeException.class, order::accept);
    }

    @Test
    @DisplayName("주문 거절")
    void reject() {
        order.reject();
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.REJECTED);
    }

    @Test
    @DisplayName("올바르지 않은 주문 거절")
    void invalid_reject() {
        order.reject();
        assertThrows(InvalidOrderStatusChangeException.class, order::reject);
    }

    @Test
    @DisplayName("주문 완료")
    void finish() {
        order.accept();
        order.finish();
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.FINISHED);
    }

    @Test
    @DisplayName("올바르지 않은 주문 완료")
    void invalid_finish() {
        assertThrows(InvalidOrderStatusChangeException.class, order::finish);
    }
}