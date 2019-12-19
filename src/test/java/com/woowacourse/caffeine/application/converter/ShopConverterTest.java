package com.woowacourse.caffeine.application.converter;

import com.woowacourse.caffeine.application.dto.ShopCreateRequest;
import com.woowacourse.caffeine.application.dto.ShopResponse;
import com.woowacourse.caffeine.domain.Shop;
import com.woowacourse.caffeine.mock.ShopRequestRepository;
import com.woowacourse.caffeine.mock.ShopResponseRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class ShopConverterTest {

    @Test
    void convertToShopResponse() {
        // given & when
        final long shopId = 1L;
        final Shop shop = ShopResponseRepository.shop1;
        final Shop mockShop = spy(shop);
        when(mockShop.getId()).thenReturn(shopId);

        // then
        final ShopResponse shopResponse = ShopConverter.convertToDto(mockShop);
        assertThat(shopResponse.getId()).isEqualTo(shopId);
        assertThat(shopResponse.getName()).isEqualTo(shop.getName());
        assertThat(shopResponse.getImage()).isEqualTo(shop.getImage());
        assertThat(shopResponse.getAddress()).isEqualTo(shop.getAddress());
        assertThat(shopResponse.getPhoneNumber()).isEqualTo(shop.getPhoneNumber());
    }

    @Test
    void convertToShop() {
        // given & when
        final ShopCreateRequest shopCreateRequest = ShopRequestRepository.shopCreateRequest;

        // then
        final Shop shop = Shop.create(shopCreateRequest);
        assertThat(shop.getName()).isEqualTo(shopCreateRequest.getName());
        assertThat(shop.getImage()).isEqualTo(shopCreateRequest.getImage());
        assertThat(shop.getAddress()).isEqualTo(shopCreateRequest.getAddress());
        assertThat(shop.getPhoneNumber()).isEqualTo(shopCreateRequest.getPhoneNumber());
    }
}