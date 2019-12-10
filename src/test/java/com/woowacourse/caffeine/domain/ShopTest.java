package com.woowacourse.caffeine.domain;

import com.woowacourse.caffeine.domain.exception.InvalidShopNameException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ShopTest {

    @Test
    void create() {
        // given
        String name = "어디야 커피";

        // when & then
        assertDoesNotThrow(() -> new Shop(name));
    }

    @Test
    void empty_name() {
        // given
        String name = "";

        // when & then
        assertThrows(InvalidShopNameException.class, () -> new Shop(name));
    }
}
