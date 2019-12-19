package com.woowacourse.caffeine.application.converter;

import com.woowacourse.caffeine.application.dto.ShopResponse;
import com.woowacourse.caffeine.domain.Shop;

public class ShopConverter {
    public static ShopResponse convertToDto(final Shop shop) {
        return new ShopResponse(
            shop.getId(),
            shop.getName(),
            shop.getImage(),
            shop.getAddress(),
            shop.getPhoneNumber());
    }
}
