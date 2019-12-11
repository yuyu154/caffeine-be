package com.woowacourse.caffeine.domain;

import com.woowacourse.caffeine.domain.exception.InvalidMenuItemNameException;
import com.woowacourse.caffeine.domain.exception.InvalidMenuItemPriceException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class MenuItem {

    public static final int PRICE_MIN_INCLUSIVE = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private int price;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop vendor;

    protected MenuItem() {
    }

    public MenuItem(String name, String description, int price, Shop vendor) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.vendor = vendor;

        if (name.isEmpty()) {
            throw new InvalidMenuItemNameException(name);
        }

        if (price < PRICE_MIN_INCLUSIVE) {
            throw new InvalidMenuItemPriceException(price);
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }
}
