package com.woowacourse.caffeine.domain;

import com.woowacourse.caffeine.domain.exception.InvalidPasswordException;
import com.woowacourse.caffeine.domain.exception.InvalidShopAddressException;
import com.woowacourse.caffeine.domain.exception.InvalidEmailException;
import com.woowacourse.caffeine.domain.exception.InvalidShopNameException;
import com.woowacourse.caffeine.domain.exception.PasswordMisMatchException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OwnerTest {

    @Test
    void password_mismatch() {
        Owner owner = new Owner("어디야 커피 잠실점", "서울특별시 송파구 석촌호수로 262 (송파동)", "kangmin789@naver.com", "P@ssWord!@");;
        assertThrows(PasswordMisMatchException.class, () -> owner.authenticate("abc"));
    }

    @Test
    void check_invalid_shop_name() {
        assertThrows(InvalidShopNameException.class, () -> new Owner("!!@@@", "서울특별시 송파구 석촌호수로 262 (송파동)", "kangmin@gmail.com", "P@ssWord!@"));
    }

    @Test
    void check_invalid_shop_name_over_size() {
        String testString = getTestString(21);
        assertThrows(InvalidShopNameException.class, () -> new Owner(testString, "서울특별시 송파구 석촌호수로 262 (송파동)", "kangmin@gmail.com", "P@ssWord!@"));
    }

    @Test
    void check_shop_name_blank_empty() {
        assertThrows(InvalidShopNameException.class, () -> new Owner(" ", "서울특별시 송파구 석촌호수로 262 (송파동)", "kangmin@gmail.com", "P@ssWord!@"));
        assertThrows(InvalidShopNameException.class, () -> new Owner("", "서울특별시 송파구 석촌호수로 262 (송파동)", "kangmin@gmail.com", "P@ssWord!@"));
    }

    @Test
    void check_shop_address_special_character() {
        assertThrows(InvalidShopAddressException.class, () -> new Owner("어디야 커피 잠실점", "!!@@", "kangmin@gmail.com", "P@ssWord!@"));
    }

    @Test
    void check_shop_address_over_size() {
        String testString = getTestString(101);
        assertThrows(InvalidShopNameException.class, () -> new Owner(testString, "서울특별시 송파구 석촌호수로 262 (송파동)", "kangmin@gmail.com", "P@ssWord!@"));
    }

    @Test
    void check_shop_address_blank() {
        assertThrows(InvalidShopAddressException.class, () -> new Owner("어디야 커피 잠실점", " ", "kangmin@gmail.com", "P@ssWord!@"));
        assertThrows(InvalidShopAddressException.class, () -> new Owner("어디야 커피 잠실점", "", "kangmin@gmail.com", "P@ssWord!@"));
    }

    @Test
    void check_email_not_special_character() {
        assertThrows(InvalidEmailException.class, () -> new Owner("어디야 커피 잠실점", "서울특별시 송파구 석촌호수로 262 (송파동)", "kangmingmail.com", "P@ssWord!@"));
    }

    @Test
    void check_email_blank_empty() {
        assertThrows(InvalidEmailException.class, () -> new Owner("어디야 커피 잠실점", "서울특별시 송파구 석촌호수로 262 (송파동)", " ", "P@ssWord!@"));
        assertThrows(InvalidEmailException.class, () -> new Owner("어디야 커피 잠실점", "서울특별시 송파구 석촌호수로 262 (송파동)", "", "P@ssWord!@"));
    }

    @Test
    void check_password_not_special_character() {
        assertThrows(InvalidPasswordException.class, () -> new Owner("어디야 커피 잠실점", "서울특별시 송파구 석촌호수로 262 (송파동)", "kangmin@gmail.com", "PssWord"));
    }

    @Test
    void check_password_blank_empty() {
        assertThrows(InvalidPasswordException.class, () -> new Owner("어디야 커피 잠실점", "서울특별시 송파구 석촌호수로 262 (송파동)", "kangmin@gmail.com", " "));
        assertThrows(InvalidPasswordException.class, () -> new Owner("어디야 커피 잠실점", "서울특별시 송파구 석촌호수로 262 (송파동)", "kangmin@gmail.com", ""));
    }

    private String getTestString(int size) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            stringBuilder.append("a");
        }
        return stringBuilder.toString();
    }
}
