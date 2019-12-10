package com.woowacourse.caffeine.mock;

import com.woowacourse.caffeine.application.dto.ShopResponse;
import com.woowacourse.caffeine.application.dto.ShopResponses;
import com.woowacourse.caffeine.domain.Shop;

import java.util.Arrays;

public class ShopResponseRepository {

    public static final ShopResponse shopResponse1 = new ShopResponse(
        100L,
        "어디야 커피 잠실점",
        "https://github.com/eunsukko/TIL/blob/master/201912/caffeine/pictures/starbucks_%EC%84%9D%EC%B4%8C%ED%98%B8%EC%88%98.jpg?raw=true",
        "서울특별시 송파구 석촌호수로 262 (송파동)",
        "02-758-8693");

    public static final ShopResponse shopResponse2 = new ShopResponse(
        101L,
        "석촌 호수",
        "https://github.com/eunsukko/TIL/blob/master/201912/caffeine/pictures/starbucks_%EC%86%A1%ED%8C%8C%EA%B5%AC%EC%B2%AD.jpeg?raw=true",
        "서울특별시 송파구 오금로 142 (송파동)",
        "02-421-3622");

    public static final ShopResponses shopResponses =
        new ShopResponses(Arrays.asList(shopResponse1, shopResponse2));

    public static final Shop shop1 = new Shop(
        shopResponse1.getName(),
        shopResponse1.getImage(),
        shopResponse1.getAddress(),
        shopResponse1.getPhoneNumber());

    public static final Shop shop2 = new Shop(
        shopResponse2.getName(),
        shopResponse2.getImage(),
        shopResponse2.getAddress(),
        shopResponse2.getPhoneNumber()
    );
}
