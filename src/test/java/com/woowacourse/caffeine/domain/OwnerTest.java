package com.woowacourse.caffeine.domain;

import com.woowacourse.caffeine.domain.exception.InvalidEmailException;
import com.woowacourse.caffeine.domain.exception.InvalidPasswordException;
import com.woowacourse.caffeine.domain.exception.PasswordMisMatchException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.woowacourse.caffeine.presentation.controller.OwnerController.DEFAULT_IMAGE;
import static com.woowacourse.caffeine.presentation.controller.OwnerController.DEFAULT_PHONE_NUMBER;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OwnerTest {
    private Shop shop;

    @BeforeEach
    void setUp() {
        shop = new Shop("어디야 커피 잠실점", DEFAULT_IMAGE, "서울특별시 송파구 석촌호수로 262 (송파동)", DEFAULT_PHONE_NUMBER);
    }

    @Test
    void password_mismatch() {
        Owner owner = new Owner("kangmin789@naver.com", "p@ssW0rd", shop);
        assertThrows(PasswordMisMatchException.class, () -> owner.authenticate("abc"));
    }

    @Test
    void check_email_not_special_character() {
        assertThrows(InvalidEmailException.class, () -> new Owner("kangmingmail.com", "P@ssWord!@", shop));
    }

    @Test
    void check_email_blank_empty() {
        assertThrows(InvalidEmailException.class, () -> new Owner(" ", "P@ssWord!@", shop));
        assertThrows(InvalidEmailException.class, () -> new Owner("", "P@ssWord!@", shop));
    }

    @Test
    void check_password_not_special_character() {
        assertThrows(InvalidPasswordException.class, () -> new Owner("kangmin@gmail.com", "PssWord", shop));
    }

    @Test
    void check_password_not_special() {
        assertDoesNotThrow(() -> new Owner("kangmin@gmail.com", "p@ssW0rd", shop));
    }

    @Test
    void check_password_blank_empty() {
        assertThrows(InvalidPasswordException.class, () -> new Owner("kangmin@gmail.com", " ", shop));
        assertThrows(InvalidPasswordException.class, () -> new Owner("kangmin@gmail.com", "", shop));
    }
}
