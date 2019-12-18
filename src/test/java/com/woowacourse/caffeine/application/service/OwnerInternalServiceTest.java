package com.woowacourse.caffeine.application.service;

import com.woowacourse.caffeine.application.dto.LoginRequest;
import com.woowacourse.caffeine.application.dto.SignUpRequest;
import com.woowacourse.caffeine.application.exception.EmailDuplicateException;
import com.woowacourse.caffeine.application.exception.OwnerNotFoundException;
import com.woowacourse.caffeine.domain.exception.PasswordMisMatchException;
import com.woowacourse.caffeine.domain.Owner;
import com.woowacourse.caffeine.repository.OwnerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OwnerInternalServiceTest {

    @InjectMocks
    private OwnerInternalService ownerInternalService;

    @Mock
    private OwnerRepository ownerRepository;

    @Test
    void authenticate_fail_password_mismatch() {
        LoginRequest loginRequest = new LoginRequest("kangmin789@naver.com", "P@ssWrod!!");
        Owner owner = new Owner("어디야 커피 잠실점", "서울특별시 송파구 석촌호수로 262 (송파동)", "kangmin789@naver.com", "Pass@word!@");
        when(ownerRepository.findByEmail(any())).thenReturn(Optional.of(owner));

        assertThrows(PasswordMisMatchException.class, () -> ownerInternalService.authenticate(loginRequest));
    }

    @Test
    void authenticate_success() {
        LoginRequest loginRequest = new LoginRequest("kangmin789@naver.com", "P@ssWord!!");
        Owner owner = new Owner("어디야 커피 잠실점", "서울특별시 송파구 석촌호수로 262 (송파동)", "kangmin789@naver.com", "P@ssWord!!");

        when(ownerRepository.findByEmail(any())).thenReturn(Optional.of(owner));

        assertThat(ownerInternalService.authenticate(loginRequest)).isNotNull();
    }

    @Test
    void authenticate_fail_email_duplicate() {
        SignUpRequest signUpRequest = new SignUpRequest("kangmin789@naver.com", "P@ssWord!@", "어디야 커피 잠실점", "서울특별시 송파구 석촌호수로 262 (송파동)");
        Owner owner = new Owner("어디야 커피 잠실점", "서울특별시 송파구 석촌호수로 262 (송파동)", "kangmin789@naver.com", "P@ssWord!!");

        when(ownerRepository.findByEmail(any())).thenReturn(Optional.of(owner));

        assertThrows(EmailDuplicateException.class, () -> ownerInternalService.save(signUpRequest));
    }
}
