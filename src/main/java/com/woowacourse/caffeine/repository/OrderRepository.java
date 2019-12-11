package com.woowacourse.caffeine.repository;

import com.woowacourse.caffeine.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
