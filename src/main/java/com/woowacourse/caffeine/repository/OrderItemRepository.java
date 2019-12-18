package com.woowacourse.caffeine.repository;

import com.woowacourse.caffeine.domain.MenuItem;
import com.woowacourse.caffeine.domain.Order;
import com.woowacourse.caffeine.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query("select o.menuItem from OrderItem o where o.order = :orders")
    List<MenuItem> findAllMenuItemByOrder(@Param("orders") final Order order);

    List<OrderItem> findAllByOrder(final Order order);
}
