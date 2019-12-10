package com.woowacourse.caffeine.application.service;

import com.woowacourse.caffeine.application.dto.ShopCreateRequest;
import com.woowacourse.caffeine.application.dto.ShopResponse;
import com.woowacourse.caffeine.application.dto.ShopResponses;
import com.woowacourse.caffeine.domain.Shop;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Transactional
public class ShopService {

    private final ShopInternalService shopInternalService;

    public ShopService(final ShopInternalService shopInternalService) {
        this.shopInternalService = shopInternalService;
    }

    public ShopResponse createShop(final ShopCreateRequest request) {
        Shop shop = shopInternalService.createShop(request);
        return new ShopResponse(
            shop.getId(),
            shop.getName(),
            shop.getImageUrl(),
            shop.getAddress(),
            shop.getPhoneNumber());
    }

    @Transactional(readOnly = true)
    public ShopResponse findById(final long id) {
        Shop shop = shopInternalService.findById(id);
        return new ShopResponse(
            shop.getId(),
            shop.getName(),
            shop.getImageUrl(),
            shop.getAddress(),
            shop.getPhoneNumber());
    }

    @Transactional(readOnly = true)
    public ShopResponses findAll() {
        List<Shop> shops = shopInternalService.findAll();
        List<ShopResponse> shopResponses = shops.stream()
            .map(shop -> new ShopResponse(shop.getId(), shop.getName(), shop.getImageUrl(), shop.getAddress(), shop.getPhoneNumber()))
            .collect(toList());
        return new ShopResponses(shopResponses);
    }
}
