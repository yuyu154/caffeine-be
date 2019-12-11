package com.woowacourse.caffeine.domain;

import com.woowacourse.caffeine.domain.exception.InvalidShopNameException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ShopTest {

    @Test
    void create() {
        // given
        final String name = "어디야 커피";
        final String imageUrl = "https://github.com/eunsukko/TIL/blob/master/201912/caffeine/pictures/starbucks_%EC%84%9D%EC%B4%8C%ED%98%B8%EC%88%98.jpg?raw=true";
        final String address = "서울특별시 송파구 석촌호수로 262 (송파동)";
        final String phoneNumber = "02-758-8693";

        // when & then
        assertDoesNotThrow(() -> new Shop(name, imageUrl, address, phoneNumber));
    }

    @Test
    void empty_name() {
        // given
        final String name = "";
        final String imageUrl = "https://github.com/eunsukko/TIL/blob/master/201912/caffeine/pictures/starbucks_%EC%84%9D%EC%B4%8C%ED%98%B8%EC%88%98.jpg?raw=true";
        final String address = "서울특별시 송파구 석촌호수로 262 (송파동)";
        final String phoneNumber = "02-758-8693";

        // when & then
        assertThrows(InvalidShopNameException.class, () -> new Shop(name, imageUrl, address, phoneNumber));
    }
}
