package com.woowacourse.caffeine.application.service;

import com.woowacourse.caffeine.application.converter.ShopConverter;
import com.woowacourse.caffeine.application.dto.ShopCreateRequest;
import com.woowacourse.caffeine.application.dto.ShopResponse;
import com.woowacourse.caffeine.application.dto.ShopResponses;
import com.woowacourse.caffeine.application.dto.ShopSearchDto;
import com.woowacourse.caffeine.domain.SearchKeyword;
import com.woowacourse.caffeine.domain.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Transactional
public class ShopService {

    private final ShopInternalService shopInternalService;

    public ShopService(ShopInternalService shopInternalService) {
        this.shopInternalService = shopInternalService;
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
        return ShopConverter.convertToDto(shop);
    }

    @Transactional(readOnly = true)
    public Page<ShopResponse> search(final String keyword, final String contents, final Pageable pageable) {
        ShopSearchDto shopSearchDto = new ShopSearchDto(keyword, contents);
        return searchShopPage(pageable, shopSearchDto)
            .map(ShopConverter::convertToDto);
    }

    private Page<Shop> searchShopPage(Pageable pageable, ShopSearchDto shopSearchDto) {
        return shopInternalService.search(shopSearchDto, pageable,
            SearchKeyword.of(shopSearchDto.getKeyword()).getShopSearchFunction());
    }
}
