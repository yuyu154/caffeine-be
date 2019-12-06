package com.woowacourse.caffeine.application.service;

import com.woowacourse.caffeine.application.exception.ShopNotFoundException;
import com.woowacourse.caffeine.application.dto.ShopCreateRequest;
import com.woowacourse.caffeine.domain.Shop;
import com.woowacourse.caffeine.repository.ShopRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
class ShopInternalService {

    private final ShopRepository shopRepository;

    public ShopInternalService(final ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    public Shop createShop(final ShopCreateRequest request) {
        return shopRepository.save(new Shop(request.getName()));
    }

    public Shop findById(final long id) {
        return shopRepository.findById(id)
            .orElseThrow(() -> new ShopNotFoundException(id));
    }
}
