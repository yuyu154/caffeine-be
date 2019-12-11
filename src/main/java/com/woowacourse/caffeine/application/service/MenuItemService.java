package com.woowacourse.caffeine.application.service;

import com.woowacourse.caffeine.application.dto.MenuCreateRequest;
import com.woowacourse.caffeine.application.dto.MenuItemResponse;
import com.woowacourse.caffeine.application.dto.MenuItemUpdateRequest;
import com.woowacourse.caffeine.domain.MenuItem;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MenuItemService {

    private final MenuItemInternalService menuItemInternalService;

    public MenuItemService(final MenuItemInternalService menuItemInternalService) {
        this.menuItemInternalService = menuItemInternalService;
    }

    @Transactional(readOnly = true)
    public List<MenuItemResponse> findByShopId(final long shopId) {
        return menuItemInternalService.findByShopId(shopId)
            .stream()
            .map(this::convertToResponse)
            .collect(Collectors.toList());
    }

    public MenuItemResponse createMenuItem(final MenuCreateRequest menuCreateRequest) {
        MenuItem menuItem = menuItemInternalService.createMenuItem(menuCreateRequest);
        return convertToResponse(menuItem);
    }

    @Transactional(readOnly = true)
    public MenuItemResponse findByMenuItemId(final long menuItemId) {
        MenuItem menuItem = menuItemInternalService.findByMenuItemId(menuItemId);
        return convertToResponse(menuItem);
    }

    public MenuItemResponse updateMenuItem(final long menuItemId, final MenuItemUpdateRequest menuItemUpdateRequest) {
        MenuItem updatedMenuItem = menuItemInternalService.updateMenuItem(menuItemId, menuItemUpdateRequest);
        return convertToResponse(updatedMenuItem);
    }

    public void deleteMenuItem(final long menuItemId) {
        menuItemInternalService.deleteMenuItem(menuItemId);
    }

    private MenuItemResponse convertToResponse(final MenuItem menuItem) {
        return new MenuItemResponse(
            menuItem.getId(),
            menuItem.getName(),
            menuItem.getNameInEnglish(),
            menuItem.getDescription(),
            menuItem.getPrice(),
            menuItem.getImgUrl(),
            menuItem.getCategory(),
            menuItem.getVendor()
        );
    }
}
