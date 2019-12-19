package com.woowacourse.caffeine.domain;

import com.woowacourse.caffeine.domain.exception.InvalidOrderStatusChangeException;

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

    @Enumerated(value = EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    private OrderStatus orderStatus;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    private String customerId;

    protected Order() {
    }

    public Order(final OrderStatus orderStatus, final Shop shop, final String customerId) {
        this.orderStatus = orderStatus;
        this.shop = shop;
        this.customerId = customerId;
    }

    public static Order createOrder(final Shop shop, final String customerId) {
        return new Order(OrderStatus.PENDING, shop, customerId);
    }

    public void accept() {
        if (orderStatus == OrderStatus.PENDING) {
            orderStatus = OrderStatus.IN_PROGRESS;
            return;
        }
        throw new InvalidOrderStatusChangeException(orderStatus, OrderStatus.IN_PROGRESS);
    }

    public void reject() {
        if (orderStatus == OrderStatus.PENDING) {
            orderStatus = OrderStatus.REJECTED;
            return;
        }
        throw new InvalidOrderStatusChangeException(orderStatus, OrderStatus.REJECTED);
    }

    public void finish() {
        if (orderStatus == OrderStatus.IN_PROGRESS) {
            orderStatus = OrderStatus.FINISHED;
            return;
        }
        throw new InvalidOrderStatusChangeException(orderStatus, OrderStatus.FINISHED);
    }

    public Long getId() {
        return id;
    }

    public Shop getShop() {
        return shop;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public String getCustomerId() {
        return customerId;
    }
}
