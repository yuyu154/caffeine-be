package com.woowacourse.caffeine.domain;

import com.woowacourse.caffeine.domain.exception.InvalidShopNameException;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Shop {
    private static final String DEFAULT_IMAGE =
        "https://github.com/eunsukko/TIL/blob/master/201912/caffeine/pictures/" +
            "starbucks_%EC%84%9D%EC%B4%8C%ED%98%B8%EC%88%98.jpg?raw=true";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String imageUrl;

    private String address;

    private String phoneNumber;

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

    public Shop(final String name, final String imageUrl, final String address, final String phoneNumber) {
        this.name = name;

        if (name.isEmpty()) {
            throw new InvalidShopNameException(name);
        }

        configImage(imageUrl);
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    private void configImage(final String imageUrl) {
        if (StringUtils.isEmpty(imageUrl)) {
            this.imageUrl = DEFAULT_IMAGE;
            return;
        }
        this.imageUrl = imageUrl;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
