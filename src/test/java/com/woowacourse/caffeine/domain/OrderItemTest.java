package com.woowacourse.caffeine.domain;

import com.woowacourse.caffeine.mock.ShopResponseRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OrderItemTest {

    @Test
    void createOrderItem() {

        final Shop shop = ShopResponseRepository.shop1;
        final Order order = Order.createOrder(shop, "1");
        final MenuItem menuItem = MenuItem.builder()
            .name("name")
            .nameInEnglish("name in english")
            .price(500)
            .imgUrl("https://img.url")
            .description("description")
            .category("category")
            .vendor(shop).build();

        final OrderItem orderItem = OrderItem.createOrderItem(order, menuItem);

        assertNotNull(orderItem);
        assertNotNull(orderItem.getOrder());
        assertNotNull(orderItem.getMenuItem());
    }
}