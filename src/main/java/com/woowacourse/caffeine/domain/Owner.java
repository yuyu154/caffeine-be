package com.woowacourse.caffeine.domain;

import com.woowacourse.caffeine.domain.exception.InvalidEmailException;
import com.woowacourse.caffeine.domain.exception.InvalidPasswordException;
import com.woowacourse.caffeine.domain.exception.InvalidShopAddressException;
import com.woowacourse.caffeine.domain.exception.InvalidShopNameException;
import com.woowacourse.caffeine.domain.exception.PasswordMisMatchException;
import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.regex.Pattern;

@Entity
public class Owner {

    private static final String SHOP_NAME_REGEX = "^[a-zA-Z0-9가-힣\\s]{1,20}$";
    private static final String SHOP_ADDRESS_REGEX = "^[a-zA-Z0-9가-힣\\s()]{1,100}$";
    private static final String EMAIL_REGEX = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";
    private static final String PASSWORD_REGEX = "^(?=.*\\d{1,50})(?=.*[~`!@#$%\\^&*()-+=]{1,50})(?=.*[a-zA-Z]{2,50}).{8,50}$";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToOne
    private Shop shop;

    public Owner(final String email, final String password, final Shop shop) {
        String trimedEmail = email.trim();
        checkValid(trimedEmail, password);
        this.email = trimedEmail;
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
        this.shop = shop;
    }

    protected Owner() {
    }

    private void checkValid(final String email, final String password) {
        checkEmail(email);
        checkPassword(password);
    }

    private void checkShopName(final String shopName) {
        if (shopName.trim().isEmpty() || !Pattern.matches(SHOP_NAME_REGEX, shopName)) {
            throw new InvalidShopNameException(shopName);
        }
    }

    private void checkShopAddress(final String shopAddress) {
        if (shopAddress.trim().isEmpty() || !Pattern.matches(SHOP_ADDRESS_REGEX, shopAddress)) {
            throw new InvalidShopAddressException();
        }
    }

    private void checkEmail(final String email) {
        if (email.trim().isEmpty() || !Pattern.matches(EMAIL_REGEX, email)) {
            throw new InvalidEmailException();
        }
    }

    private void checkPassword(final String password) {
        if (password.trim().isEmpty() || !Pattern.matches(PASSWORD_REGEX, password)) {
            throw new InvalidPasswordException();
        }
    }

    public void authenticate(final String password) {
        if (!BCrypt.checkpw(password, this.password)) {
            throw new PasswordMisMatchException();
        }
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Shop getShop() {
        return shop;
    }
}
