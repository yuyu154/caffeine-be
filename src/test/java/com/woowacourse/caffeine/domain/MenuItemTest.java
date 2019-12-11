package com.woowacourse.caffeine.domain;

import com.woowacourse.caffeine.domain.exception.InvalidMenuItemNameException;
import com.woowacourse.caffeine.domain.exception.InvalidMenuItemNameInEnglishException;
import com.woowacourse.caffeine.domain.exception.InvalidMenuItemPriceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MenuItemTest {

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
    void create() {
        // when & then
        assertDoesNotThrow(() -> new MenuItem(name, nameInEnglish, description, price, img, category, shop));
    }

    @Test
    void name_empty() {
        // given
        String emptyName = "";

        // when & then
        assertThrows(InvalidMenuItemNameException.class, () -> new MenuItem(emptyName, nameInEnglish, description, price, img, category, shop));
    }

    @Test
    void nameInEnglish_with_korean() {
        // given
        String nameInEnglishWithKorean = "암에리카노";

        // when & then
        assertThrows(InvalidMenuItemNameInEnglishException.class, () -> new MenuItem(name, nameInEnglishWithKorean, description, price, img, category, shop));
    }

    @Test
    void price_minus() {
        // given
        int minusPrice = -2500;

        // when & then
        assertThrows(InvalidMenuItemPriceException.class, () -> new MenuItem(name, nameInEnglish, description, minusPrice, img, category, shop));
    }
}
