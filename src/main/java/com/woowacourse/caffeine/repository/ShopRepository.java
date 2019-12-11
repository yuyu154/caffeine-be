package com.woowacourse.caffeine.repository;

import com.woowacourse.caffeine.domain.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop, Long> {
}
