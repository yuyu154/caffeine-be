package com.woowacourse.caffeine.domain;

import com.woowacourse.caffeine.domain.exception.InvalidOrderChangeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderTest {

    String name;
    String nameInEnglish;
    String description;
    int price;
    String img;
    String category;
    Shop shop;

    @BeforeEach
    void setUp() {
        name = "아메리카노";
        nameInEnglish = "Americano";
        description = "아메리카노 좋아~ 좋아~ 좋아";
        price = 2500;
        img = "abc";
        category = "coffee";
        shop = new Shop("어디야 커피");
    }

    @Test
    void changeStatusPendingToInProgress() {
        final MenuItem menuItem = new MenuItem(name, nameInEnglish, description, price, img, category, shop);
        final Order order = Order.createOrder(menuItem);
        order.changeStatus(OrderStatus.IN_PROGRESS);
    }

    @Test
    void changeStatusInProgressToFinished() {
        final MenuItem menuItem = new MenuItem(name, nameInEnglish, description, price, img, category, shop);
        final Order order = Order.createOrder(menuItem);
        assertDoesNotThrow(() -> order.changeStatus(OrderStatus.IN_PROGRESS));
        assertDoesNotThrow(() -> order.changeStatus(OrderStatus.FINISHED));
    }

    @Test
    void changeStatusException() {
        final MenuItem menuItem = new MenuItem(name, nameInEnglish, description, price, img, category, shop);
        final Order order = Order.createOrder(menuItem);
        assertThrows(InvalidOrderChangeException.class, () -> order.changeStatus(OrderStatus.FINISHED));
    }
}