package com.woowacourse.caffeine.application.service;

import com.woowacourse.caffeine.application.converter.OwnerConverter;
import com.woowacourse.caffeine.application.dto.LoginRequest;
import com.woowacourse.caffeine.application.dto.ShopCreateRequest;
import com.woowacourse.caffeine.application.dto.SignUpRequest;
import com.woowacourse.caffeine.application.exception.EmailDuplicateException;
import com.woowacourse.caffeine.application.exception.OwnerNotFoundException;
import com.woowacourse.caffeine.domain.Owner;
import com.woowacourse.caffeine.domain.Shop;
import com.woowacourse.caffeine.repository.OwnerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Service
public class OwnerInternalService {

    private final OwnerRepository ownerRepository;
    private final ShopInternalService shopInternalService;

    public OwnerInternalService(final OwnerRepository ownerRepository, final ShopInternalService shopInternalService) {
        this.ownerRepository = ownerRepository;
        this.shopInternalService = shopInternalService;
    }

    public Long save(final SignUpRequest signUpRequest, final ShopCreateRequest shopCreateRequest) {
        if (ownerRepository.findByEmail(signUpRequest.getEmail()).isPresent()) {
            throw new EmailDuplicateException();
        }

        Shop shop = shopInternalService.createShop(shopCreateRequest);
        Owner owner = OwnerConverter.convertToEntity(signUpRequest, shop);
        return ownerRepository.save(owner).getId();
    }

    @Transactional(readOnly = true)
    public String authenticate(final LoginRequest loginRequest) {
        Owner owner = ownerRepository.findByEmail(loginRequest.getEmail())
            .orElseThrow(OwnerNotFoundException::new);
        owner.authenticate(loginRequest.getPassword());
        return owner.getEmail();
    }

    @Transactional(readOnly = true)
    public Owner findByEmail(final String email) {
        return ownerRepository.findByEmail(email)
            .orElseThrow(OwnerNotFoundException::new);
    }
}
