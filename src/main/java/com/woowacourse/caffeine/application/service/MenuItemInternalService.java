package com.woowacourse.caffeine.application.service;

import com.woowacourse.caffeine.domain.MenuItem;
import com.woowacourse.caffeine.domain.Shop;
import com.woowacourse.caffeine.repository.MenuItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MenuItemInternalService {

    private final ShopInternalService shopInternalService;
    private final MenuItemRepository menuItemRepository;

    public MenuItemInternalService(final ShopInternalService shopInternalService, final MenuItemRepository menuItemRepository) {
        this.shopInternalService = shopInternalService;
        this.menuItemRepository = menuItemRepository;
    }

    public List<MenuItem> findByShopId(final long shopId) {
        Shop vendor = shopInternalService.findById(shopId);
        return menuItemRepository.findByVendor(vendor);
    }
}
