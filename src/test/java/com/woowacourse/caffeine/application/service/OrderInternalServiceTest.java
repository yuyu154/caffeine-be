package com.woowacourse.caffeine.application.service;

import com.woowacourse.caffeine.application.dto.OrderCreateRequest;
import com.woowacourse.caffeine.domain.MenuItem;
import com.woowacourse.caffeine.domain.Order;
import com.woowacourse.caffeine.domain.Shop;
import com.woowacourse.caffeine.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class OrderInternalServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private MenuItemInternalService menuItemInternalService;

    @Mock
    private ShopInternalService shopInternalService;

    @Mock
    private ShopNotificationService shopNotificationService;

    @Mock
    private CustomerNotificationService customerNotificationService;

    @InjectMocks
    private OrderInternalService orderInternalService;

    @Test
    @DisplayName("주문 생성")
    void create() {
        // given
        String shopName = "Caffe";
        String orderItemName = "아메리카노";
        Shop shop = new Shop(shopName, "", "", "");
        MenuItem menuItem = new MenuItem(orderItemName, "", "", 2500, "", "", shop);
        long shopId = 102L;
        long menuItemId = 100L;
        when(shopInternalService.findById(shopId)).thenReturn(shop);
        when(menuItemInternalService.findById(menuItemId)).thenReturn(menuItem);
        when(orderRepository.save(any())).thenReturn(Order.createOrder(shop, menuItem, ""));

        // when
        Order created = orderInternalService.create(shopId, new OrderCreateRequest(menuItemId, ""));

        // then
        assertThat(created.getMenuItem().getName()).isEqualTo(orderItemName);
        assertThat(created.getShop().getName()).isEqualTo(shopName);
        verify(shopNotificationService, atLeastOnce()).send(anyLong(), anyString());
    }
}
