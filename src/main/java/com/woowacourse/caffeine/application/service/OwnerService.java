package com.woowacourse.caffeine.application.service;

import com.woowacourse.caffeine.application.dto.LoginRequest;
import com.woowacourse.caffeine.application.dto.OwnerResponse;
import com.woowacourse.caffeine.application.dto.SignUpRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OwnerService {

    private final OwnerInternalService ownerInternalService;

    public OwnerService(final OwnerInternalService ownerInternalService) {
        this.ownerInternalService = ownerInternalService;
    }

    public Long signUp(final SignUpRequest signUpRequest) {
        return ownerInternalService.save(signUpRequest);
    }

    @Transactional(readOnly = true)
    public String authenticate(final LoginRequest loginRequest) {
        return ownerInternalService.authenticate(loginRequest);
    }

    @Transactional(readOnly = true)
    public OwnerResponse findByEmail(final String email) {
        return ownerInternalService.findByEmail(email);
    }
}
