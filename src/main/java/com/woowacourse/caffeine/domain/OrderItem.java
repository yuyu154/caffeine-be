package com.woowacourse.caffeine.domain;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ORDER_ITEM")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Order order;

    @ManyToOne
    private MenuItem menuItem;

    protected OrderItem() {
    }

    private OrderItem(final Order order, final MenuItem menuItem) {
        this.order = order;
        this.menuItem = menuItem;
    }

    public static OrderItem createOrderItem(final Order order, final MenuItem menuItem) {
        return new OrderItem(order, menuItem);
    }

    public Long getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }
}
