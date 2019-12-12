package com.woowacourse.caffeine.application.exception;

public class MenuItemNotFoundException extends RuntimeException {

    public MenuItemNotFoundException(final long actualMenuItemId) {
        super(String.format("메뉴을 찾을 수 없습니다, ID: %d", actualMenuItemId));
    }
}
