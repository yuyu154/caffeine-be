package com.woowacourse.caffeine.application.service;

import com.woowacourse.caffeine.application.dto.ShopCreateRequest;
import com.woowacourse.caffeine.application.dto.ShopResponse;
import com.woowacourse.caffeine.domain.Shop;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class ShopService {

    private final ShopInternalService shopInternalService;

    public ShopService(final ShopInternalService shopInternalService) {
        this.shopInternalService = shopInternalService;
    }

    public ShopResponse createShop(final ShopCreateRequest request) {
        Shop shop = shopInternalService.createShop(request);
        return new ShopResponse(shop.getId(), shop.getName());
    }

    @Transactional(readOnly = true)
    public ShopResponse findById(final long id) {
        Shop shop = shopInternalService.findById(id);
        return new ShopResponse(shop.getId(), shop.getName());
    }
}
