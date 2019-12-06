package com.woowacourse.caffeine.application;

import com.woowacourse.caffeine.application.exception.ShopNotFoundException;
import com.woowacourse.caffeine.application.object.ShopCreateRequest;
import com.woowacourse.caffeine.domain.Shop;
import com.woowacourse.caffeine.repository.ShopRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
class ShopInternalService {

    private final ShopRepository shopRepository;

    ShopInternalService(final ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    public Shop createShop(final ShopCreateRequest request) {
        return shopRepository.save(new Shop(request.getName()));
    }

    Shop findById(final long id) {
        return shopRepository.findById(id)
            .orElseThrow(() -> new ShopNotFoundException(id));
    }
}
