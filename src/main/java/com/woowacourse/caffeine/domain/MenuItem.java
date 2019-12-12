package com.woowacourse.caffeine.domain;

import com.woowacourse.caffeine.application.dto.MenuItemUpdateRequest;
import com.woowacourse.caffeine.domain.exception.InvalidMenuItemNameException;
import com.woowacourse.caffeine.domain.exception.InvalidMenuItemNameInEnglishException;
import com.woowacourse.caffeine.domain.exception.InvalidMenuItemPriceException;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.regex.Pattern;

@Setter
@Getter
@Entity
public class MenuItem extends BaseTimeEntity {

    public static final int PRICE_MIN_INCLUSIVE = 0;
    public static final String ENGLISH_REGEX = "^[a-zA-Z0-9 ]*$";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String nameInEnglish;

    private String description;

    private int price;

    private String imgUrl;

    private String category;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop vendor;

    protected MenuItem() {
    }

    @Builder
    public MenuItem(final String name, final String nameInEnglish, final String description, final int price, final String imgUrl, final String category, final Shop vendor) {
        checkValidation(name, nameInEnglish, price);

        this.name = name;
        this.nameInEnglish = nameInEnglish;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
        this.category = category;
        this.vendor = vendor;
    }

    public void update(final MenuItemUpdateRequest menuItemUpdateRequest) {
        checkValidation(menuItemUpdateRequest.getName(), menuItemUpdateRequest.getNameInEnglish(), menuItemUpdateRequest.getPrice());

        this.name = menuItemUpdateRequest.getName();
        this.nameInEnglish = menuItemUpdateRequest.getNameInEnglish();
        this.description = menuItemUpdateRequest.getDescription();
        this.price = menuItemUpdateRequest.getPrice();
        this.imgUrl = menuItemUpdateRequest.getImgUrl();
        this.category = menuItemUpdateRequest.getCategory();
    }

    private void checkValidation(final String name, final String nameInEnglish, final int price) {
        checkName(name);
        checkNameInEnglish(nameInEnglish);
        checkPrice(price);
    }

    private void checkName(final String name) {
        if (StringUtils.isEmpty(name)) {
            throw new InvalidMenuItemNameException(name);
        }
    }

    private void checkNameInEnglish(final String nameInEnglish) {
        if (!Pattern.matches(ENGLISH_REGEX, nameInEnglish)) {
            throw new InvalidMenuItemNameInEnglishException(nameInEnglish);
        }
    }

    private void checkPrice(final int price) {
        if (price < PRICE_MIN_INCLUSIVE) {
            throw new InvalidMenuItemPriceException(price);
        }
    }
}
