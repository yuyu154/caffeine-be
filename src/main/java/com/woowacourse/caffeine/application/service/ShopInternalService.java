package com.woowacourse.caffeine.application.service;

import com.woowacourse.caffeine.application.dto.ShopCreateRequest;
import com.woowacourse.caffeine.application.dto.ShopSearchDto;
import com.woowacourse.caffeine.application.exception.ShopNotFoundException;
import com.woowacourse.caffeine.domain.Shop;
import com.woowacourse.caffeine.domain.ShopSearchFunction;
import com.woowacourse.caffeine.repository.ShopRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Transactional(readOnly = true)
    public Shop findById(final Long id) {
        return shopRepository.findById(id)
            .orElseThrow(() -> new ShopNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<Shop> findAll() {
        return shopRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Page<Shop> search(final ShopSearchDto shopSearchDto, final Pageable pageable, final ShopSearchFunction shopSearchFunction) {
        return shopSearchFunction.search(shopSearchDto.getContents(), pageable, shopRepository);
    }
}
