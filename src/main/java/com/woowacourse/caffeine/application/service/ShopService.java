package com.woowacourse.caffeine.application.service;

import com.woowacourse.caffeine.application.converter.ShopConverter;
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
    private final ShopConverter shopConverter;

    public ShopService(final ShopInternalService shopInternalService, final ShopConverter shopConverter) {
        this.shopInternalService = shopInternalService;
        this.shopConverter = shopConverter;
    }

    public ShopResponse createShop(final ShopCreateRequest request) {
        final Shop shop = shopInternalService.createShop(request);
        return convertToShopResponse(shop);
    }

    @Transactional(readOnly = true)
    public ShopResponse findById(final Long id) {
        final Shop shop = shopInternalService.findById(id);
        return convertToShopResponse(shop);
    }

    @Transactional(readOnly = true)
    public ShopResponses findAll() {
        final List<Shop> shops = shopInternalService.findAll();
        final List<ShopResponse> shopResponses = shops.stream()
            .map(this::convertToShopResponse)
            .collect(toList());

        return new ShopResponses(shopResponses);
    }

    private ShopResponse convertToShopResponse(final Shop shop) {
        return shopConverter.convertToDto(shop, ShopResponse.class);
    }
}
