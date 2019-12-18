package com.woowacourse.caffeine.domain;

import com.woowacourse.caffeine.domain.exception.InvalidPasswordException;
import com.woowacourse.caffeine.domain.exception.InvalidShopAddressException;
import com.woowacourse.caffeine.domain.exception.PasswordMisMatchException;
import com.woowacourse.caffeine.domain.exception.InvalidEmailException;
import com.woowacourse.caffeine.domain.exception.InvalidShopNameException;
import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.regex.Pattern;

@Entity
public class Owner {

    private static final String SHOP_NAME_REGEX = "^[a-zA-Z0-9가-힣\\s]{1,20}$";
    private static final String SHOP_ADDRESS_REGEX = "^[a-zA-Z0-9가-힣\\s()]{1,100}$";
    private static final String EMAIL_REGEX = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";
    private static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*d)(?=.*[$@$!%*#?&])[A-Za-zd$@$!%*#?&]{8,}$";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String shopName;

    @Column(nullable = false)
    private String shopAddress;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    public Owner(final String shopName, final String shopAddress, final String email, final String password) {
        String trimedName = shopName.trim();
        String trimedShopAddress = shopAddress.trim();
        String trimedEmail = email.trim();
        checkValid(trimedName, trimedShopAddress, trimedEmail, password);
        this.shopName = trimedName;
        this.shopAddress = trimedShopAddress;
        this.email = trimedEmail;
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    protected Owner() {
    }

    private void checkValid(final String shopName, final String shopAddress, final String email, final String password) {
        checkShopName(shopName);
        checkShopAddress(shopAddress);
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

    public String getShopName() {
        return shopName;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public String getEmail() {
        return email;
    }
}
