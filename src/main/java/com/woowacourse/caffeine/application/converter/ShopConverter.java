package com.woowacourse.caffeine.application.converter;

import com.woowacourse.caffeine.application.dto.ShopCreateRequest;
import com.woowacourse.caffeine.domain.Shop;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ShopConverter {

    private ModelMapper modelMapper;

    public ShopConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public <T> T convertToDto(final Shop shop, final Class<T> clazz) {
        return modelMapper.map(shop, clazz);
    }

    public Shop convertToShop(final ShopCreateRequest shopCreateRequest) {
        return new Shop(
            shopCreateRequest.getName(),
            shopCreateRequest.getImage(),
            shopCreateRequest.getAddress(),
            shopCreateRequest.getPhoneNumber());
    }
}
