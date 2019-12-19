package com.woowacourse.caffeine.application.converter;

import com.woowacourse.caffeine.application.dto.MenuItemResponse;
import com.woowacourse.caffeine.domain.MenuItem;

public class MenuItemConverter {

    public static MenuItemResponse convertToResponse(final MenuItem menuItem) {
        return new MenuItemResponse(
            menuItem.getId(),
            menuItem.getName(),
            menuItem.getNameInEnglish(),
            menuItem.getDescription(),
            menuItem.getPrice(),
            menuItem.getImgUrl(),
            menuItem.getCategory());
    }
}
