package com.woowacourse.caffeine.domain;

import com.woowacourse.caffeine.domain.exception.InvalidShopNameException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "vendor")
    private List<MenuItem> menus = new ArrayList<>();

    protected Shop() {
    }

    public Shop(final String name) {
        this.name = Objects.requireNonNull(name);

        if (name.isEmpty()) {
            throw new InvalidShopNameException(name);
        }
    }

    public void addMenu(final MenuItem menu) {
        menus.add(menu);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
