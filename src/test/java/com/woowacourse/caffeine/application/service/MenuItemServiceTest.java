package com.woowacourse.caffeine.application.service;

import com.woowacourse.caffeine.application.service.MenuItemService;
import com.woowacourse.caffeine.application.service.ShopInternalService;
import com.woowacourse.caffeine.domain.MenuItem;
import com.woowacourse.caffeine.domain.Shop;
import com.woowacourse.caffeine.repository.MenuItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MenuItemServiceTest {

    @Mock
    private MenuItemRepository menuItemRepository;

    @InjectMocks
    private ShopInternalService shopInternalService;

    @InjectMocks
    private MenuItemService menuItemService;

    @Test
    void find_by_shop() {
        // given
        Shop shop = new Shop("어디야 커피");
        List<MenuItem> menus = Arrays.asList(
            new MenuItem("아메리카노", "맛있는 아메리카노", 2500, shop),
            new MenuItem("카페라떼", "고소한 라떼", 3000, shop)
        );

        // when
        when(menuItemRepository.findByVendor(shop)).thenReturn(menus);
        List<MenuItem> found = menuItemRepository.findByVendor(shop);

        // then
        assertThat(found).isEqualTo(menus);
    }
}
