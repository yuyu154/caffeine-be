package com.woowacourse.caffeine.domain;

import com.woowacourse.caffeine.application.dto.ShopCreateRequest;
import com.woowacourse.caffeine.application.dto.SignUpRequest;
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

import static com.woowacourse.caffeine.presentation.controller.OwnerController.DEFAULT_IMAGE;
import static com.woowacourse.caffeine.presentation.controller.OwnerController.DEFAULT_PHONE_NUMBER;

@Entity
public class Shop extends BaseTimeEntity {

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

    public static Shop create(final ShopCreateRequest shopCreateRequest) {
        return new Shop(
            shopCreateRequest.getName(),
            shopCreateRequest.getImage(),
            shopCreateRequest.getAddress(),
            shopCreateRequest.getPhoneNumber());
    }

    public static Shop createWithoutImage(SignUpRequest signUpRequest) {
        return new Shop(signUpRequest.getShopName(), DEFAULT_IMAGE, signUpRequest.getShopAddress(), DEFAULT_PHONE_NUMBER);
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
