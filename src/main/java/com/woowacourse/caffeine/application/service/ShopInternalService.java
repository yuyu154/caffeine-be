package com.woowacourse.caffeine.application.service;

import com.woowacourse.caffeine.application.dto.ShopCreateRequest;
import com.woowacourse.caffeine.application.exception.ShopNotFoundException;
import com.woowacourse.caffeine.domain.Shop;
import com.woowacourse.caffeine.repository.ShopRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
class ShopInternalService {

    private final ShopRepository shopRepository;

    public ShopInternalService(final ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    public Shop createShop(final ShopCreateRequest shopCreateRequest) {
        return shopRepository.save(Shop.create(shopCreateRequest));
    }

    public Shop findById(final Long id) {
        return shopRepository.findById(id)
            .orElseThrow(() -> new ShopNotFoundException(id));
    }

    public List<Shop> findAll() {
        return shopRepository.findAll();
    }
}
