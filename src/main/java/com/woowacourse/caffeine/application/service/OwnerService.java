package com.woowacourse.caffeine.application.service;

import com.woowacourse.caffeine.application.converter.ShopConverter;
import com.woowacourse.caffeine.application.dto.LoginRequest;
import com.woowacourse.caffeine.application.dto.OwnerResponse;
import com.woowacourse.caffeine.application.dto.ShopCreateRequest;
import com.woowacourse.caffeine.application.dto.ShopResponse;
import com.woowacourse.caffeine.application.dto.SignUpRequest;
import com.woowacourse.caffeine.domain.Owner;
import com.woowacourse.caffeine.domain.Shop;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OwnerService {

    private final OwnerInternalService ownerInternalService;

    public OwnerService(final OwnerInternalService ownerInternalService) {
        this.ownerInternalService = ownerInternalService;
    }

    public Long signUpAndCreateShop(final SignUpRequest signUpRequest, final ShopCreateRequest shopCreateRequest) {
        return ownerInternalService.save(signUpRequest, shopCreateRequest);
    }

    @Transactional(readOnly = true)
    public String authenticate(final LoginRequest loginRequest) {
        return ownerInternalService.authenticate(loginRequest);
    }

    @Transactional(readOnly = true)
    public OwnerResponse findByEmail(final String email) {
        Owner owner = ownerInternalService.findByEmail(email);
        Shop shop = owner.getShop();
        ShopResponse shopResponse = ShopConverter.convertToDto(shop);
        return new OwnerResponse(owner.getId(), owner.getEmail(), shopResponse);
    }
}
