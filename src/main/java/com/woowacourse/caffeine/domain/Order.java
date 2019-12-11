package com.woowacourse.caffeine.domain;

import com.woowacourse.caffeine.domain.exception.InvalidOrderChangeException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ORDERS")
public class Order extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "menu_id", nullable = false)
    private MenuItem menuItem;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    private OrderStatus orderStatus;

    protected Order() {
    }

    protected Order(final MenuItem menuItem, final OrderStatus orderStatus) {
        this.menuItem = menuItem;
        this.orderStatus = orderStatus;
    }

    public static Order createOrder(final MenuItem menuItem) {
        return new Order(menuItem, OrderStatus.PENDING);
    }

    public void changeStatus(final OrderStatus orderStatus) {
        if (OrderStatus.PENDING.equals(this.orderStatus) && OrderStatus.IN_PROGRESS.equals(orderStatus)) {
            this.orderStatus = orderStatus;
            return;
        }

        if (OrderStatus.IN_PROGRESS.equals(this.orderStatus) && OrderStatus.FINISHED.equals(orderStatus)) {
            this.orderStatus = orderStatus;
            return;
        }

        throw new InvalidOrderChangeException(orderStatus.toString());
    }

    public Long getId() {
        return id;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

}
