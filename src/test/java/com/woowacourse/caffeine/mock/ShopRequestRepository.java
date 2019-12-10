package com.woowacourse.caffeine.mock;

import com.woowacourse.caffeine.application.dto.ShopCreateRequest;

public class ShopRequestRepository {
    public static final ShopCreateRequest shopCreateRequest =
        new ShopCreateRequest(
            "어디야 커피 잠실점",
            "https://github.com/eunsukko/TIL/blob/master/201912/caffeine/pictures/starbucks_%EC%84%9D%EC%B4%8C%ED%98%B8%EC%88%98.jpg?raw=true",
            "서울특별시 송파구 석촌호수로 262 (송파동)",
            "02-758-8693");
}
