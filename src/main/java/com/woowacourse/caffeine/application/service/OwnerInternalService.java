package com.woowacourse.caffeine.application.service;

import com.woowacourse.caffeine.application.converter.OwnerConverter;
import com.woowacourse.caffeine.application.dto.LoginRequest;
import com.woowacourse.caffeine.application.dto.OwnerResponse;
import com.woowacourse.caffeine.application.dto.SignUpRequest;
import com.woowacourse.caffeine.application.exception.EmailDuplicateException;
import com.woowacourse.caffeine.application.exception.OwnerNotFoundException;
import com.woowacourse.caffeine.domain.Owner;
import com.woowacourse.caffeine.repository.OwnerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Transactional
@Service
public class OwnerInternalService {

    private final OwnerRepository ownerRepository;

    public OwnerInternalService(final OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    public Long save(final SignUpRequest signUpRequest) {
        if(ownerRepository.findByEmail(signUpRequest.getEmail()).isPresent()) {
            throw new EmailDuplicateException();
        }
        Owner owner = OwnerConverter.convertToEntity(signUpRequest);
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
    public OwnerResponse findByEmail(final String email) {
        Owner owner = ownerRepository.findByEmail(email)
            .orElseThrow(OwnerNotFoundException::new);
        return OwnerConverter.convertToResponse(owner);
    }
}
