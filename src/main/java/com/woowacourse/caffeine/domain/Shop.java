package com.woowacourse.caffeine.domain;

import com.woowacourse.caffeine.application.dto.ShopCreateRequest;
import com.woowacourse.caffeine.domain.exception.InvalidShopNameException;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    @NotNull
    @NotBlank
    private String name;

    @NotNull
    private String image;

    @NotNull
    private String address;

    @NotNull
    private String phoneNumber;

    @OneToMany(mappedBy = "vendor")
    private List<MenuItem> menus = new ArrayList<>();

    protected Shop() {
    }

    public Shop(final String name) {
        this.name = Objects.requireNonNull(name);
        checkName(name);
    }

    public Shop(final String name, final String image, final String address, final String phoneNumber) {
        this.name = name;
        checkName(name);
        configImage(image);
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    private void checkName(final String name) {
        if (name.isEmpty()) {
            throw new InvalidShopNameException(name);
        }
    }

    private void configImage(final String imageUrl) {
        if (StringUtils.isEmpty(imageUrl)) {
            this.image = DEFAULT_IMAGE;
            return;
        }
        this.image = imageUrl;
    }

    public static Shop create(final ShopCreateRequest shopCreateRequest) {
        return new Shop(
            shopCreateRequest.getName(),
            shopCreateRequest.getImage(),
            shopCreateRequest.getAddress(),
            shopCreateRequest.getPhoneNumber());
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

    public String getImage() {
        return image;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
