package com.woowacourse.caffeine.repository;

import com.woowacourse.caffeine.domain.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop, Long> {
    Page<Shop> findByNameContaining(String name, Pageable pageable);

    Page<Shop> findByAddressContaining(String address, Pageable pageable);
}
