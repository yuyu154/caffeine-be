package com.woowacourse.caffeine.domain;

import com.woowacourse.caffeine.repository.ShopRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@FunctionalInterface
public interface ShopSearchFunction {
    Page<Shop> search(String keyword, Pageable pageable, ShopRepository shopRepository);
}
