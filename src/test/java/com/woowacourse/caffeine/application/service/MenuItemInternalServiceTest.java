package com.woowacourse.caffeine.application.service;

import com.woowacourse.caffeine.application.dto.MenuCreateRequest;
import com.woowacourse.caffeine.application.dto.MenuItemUpdateRequest;
import com.woowacourse.caffeine.domain.MenuItem;
import com.woowacourse.caffeine.domain.Shop;
import com.woowacourse.caffeine.repository.MenuItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MenuItemInternalServiceTest {

    @Mock
    private ShopInternalService shopInternalService;

    @Mock
    private MenuItemRepository menuItemRepository;

    @InjectMocks
    private MenuItemInternalService menuItemInternalService;

    private Shop shop;
    private MenuItem menuItem;

    @BeforeEach
    void setUp() {
        shop = new Shop("어디야 커피");
        menuItem = MenuItem.builder()
            .name("아이스 아메리카노")
            .nameInEnglish("Ice Americano")
            .description("시원한 아메리카노")
            .price(2000)
            .imgUrl("abc")
            .category("coffee")
            .vendor(shop)
            .build();
    }

    @Test
    void findByShopId() {
        // given
        MenuItem americano = MenuItem.builder()
            .name("아메리카노")
            .nameInEnglish("Americano")
            .description("맛있는 아메리카노")
            .price(2500)
            .imgUrl("https://github.com/eunsukko/TIL/blob/master/201912/caffeine/pictures/starbucks_banana.jpeg?raw=true")
            .category("coffee")
            .vendor(shop)
            .build();

        MenuItem cafeLatte = MenuItem.builder()
            .name("카페라떼")
            .nameInEnglish("cafe latte")
            .description("고소한 라떼")
            .price(3000)
            .imgUrl("https://github.com/eunsukko/TIL/blob/master/201912/caffeine/pictures/starbucks_banana.jpeg?raw=true")
            .category("coffee")
            .vendor(shop)
            .build();

        List<MenuItem> menus = Arrays.asList(americano, cafeLatte);

        // whe
        when(menuItemRepository.findByVendor(shop)).thenReturn(menus);
        List<MenuItem> found = menuItemRepository.findByVendor(shop);

        // then
        assertThat(found).isEqualTo(menus);
    }

    @Test
    void createMenuItem() {
        //given
        long shopId = 1L;
        MenuCreateRequest menuCreateRequest = new MenuCreateRequest(
            "아이스 아메리카노", "Ice Americano", "시원한 아메리카노", 2000, "abc", "coffee", shopId);

        //when
        when(menuItemRepository.save(any())).thenReturn(menuItem);
        MenuItem created = menuItemInternalService.createMenuItem(menuCreateRequest);

        //then
        assertThat(menuItem).isEqualTo(created);
    }

    @Test
    void findByMenuItemId() {
        // given
        long menuId = 1L;

        // when
        when(menuItemRepository.findById(menuId)).thenReturn(ofNullable(menuItem));
        MenuItem foundMenuItem = menuItemInternalService.findByMenuItemId(menuId);

        // then
        assertThat(foundMenuItem).isEqualTo(menuItem);
    }

    @Test
    void updateMenuItem() {
        // given
        long menuId = 1L;
        MenuItemUpdateRequest menuItemUpdateRequest = new MenuItemUpdateRequest(
            "뜨거운 아메리카노", "Hot Americano", "So Hot Ame", 2000, "abcd", "coffee");

        // when
        when(menuItemRepository.findById(menuId)).thenReturn(Optional.of(menuItem));

        // then
        MenuItem updatedMenuItem = menuItemInternalService.updateMenuItem(menuId, menuItemUpdateRequest);
        assertThat(updatedMenuItem.getName()).isEqualTo("뜨거운 아메리카노");
        assertThat(updatedMenuItem.getNameInEnglish()).isEqualTo("Hot Americano");
        assertThat(updatedMenuItem.getPrice()).isEqualTo(2000);

    }
}