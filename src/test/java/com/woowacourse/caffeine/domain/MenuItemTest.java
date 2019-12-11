package com.woowacourse.caffeine.domain;

import com.woowacourse.caffeine.domain.exception.InvalidMenuItemNameException;
import com.woowacourse.caffeine.domain.exception.InvalidMenuItemPriceException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MenuItemTest {

    @Test
    void create() {
        // given
        String name = "아메리카노";
        String description = "아메리카노 좋아~ 좋아~ 좋아~";
        int price = 2500;
        Shop shop = new Shop("어디야 커피");

        // when & then
        assertDoesNotThrow(() -> new MenuItem(name, description, price, shop));
    }

    @Test
    void name_empty() {
        // given
        String name = "";
        String description = "아메리카노 좋아~ 좋아~ 좋아~";
        int price = 2500;
        Shop shop = new Shop("어디야 커피");

        // when & then
        assertThrows(InvalidMenuItemNameException.class, () -> new MenuItem(name, description, price, shop));
    }

    @Test
    void price_minus() {
        // given
        String name = "아메리카노";
        String description = "아메리카노 좋아~ 좋아~ 좋아~";
        int price = -2500;
        Shop shop = new Shop("어디야 커피");

        // when & then
        assertThrows(InvalidMenuItemPriceException.class, () -> new MenuItem(name, description, price, shop));
    }
}
