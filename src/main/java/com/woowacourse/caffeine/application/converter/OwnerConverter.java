package com.woowacourse.caffeine.application.converter;

import com.woowacourse.caffeine.application.dto.OwnerResponse;
import com.woowacourse.caffeine.application.dto.SignUpRequest;
import com.woowacourse.caffeine.domain.Owner;

public class OwnerConverter {

    public static Owner convertToEntity(final SignUpRequest signUpRequest) {
        return new Owner(signUpRequest.getShopName(), signUpRequest.getShopAddress(), signUpRequest.getEmail(), signUpRequest.getPassword());
    }

    public static OwnerResponse convertToResponse(final Owner owner) {
        return new OwnerResponse(owner.getId(), owner.getEmail());
    }
}
