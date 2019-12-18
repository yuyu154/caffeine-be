package com.woowacourse.caffeine.application.converter;

import com.woowacourse.caffeine.application.dto.SignUpRequest;
import com.woowacourse.caffeine.domain.Owner;
import com.woowacourse.caffeine.domain.Shop;

public class OwnerConverter {

    public static Owner convertToEntity(final SignUpRequest signUpRequest, final Shop shop) {
        return new Owner(signUpRequest.getEmail(), signUpRequest.getPassword(), shop);
    }
}
