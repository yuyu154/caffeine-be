package com.woowacourse.caffeine.application.service;

import com.woowacourse.caffeine.application.dto.ShopResponse;
import com.woowacourse.caffeine.application.dto.ShopResponses;
import com.woowacourse.caffeine.domain.Shop;
import com.woowacourse.caffeine.mock.ShopResponseRepository;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;

@SpringBootTest(classes = ShopService.class)
public class ShopServiceTest {

    @Autowired
    ShopService shopService;

    @MockBean
    ShopInternalService shopInternalService;

    @SpyBean
    ModelMapper modelMapper;

    @Test
    void findAll() {

        //given & when
        ShopResponse shopResponse1 = ShopResponseRepository.shopResponse1;
        ShopResponse shopResponse2 = ShopResponseRepository.shopResponse2;

        //when
        given(shopInternalService.findAll()).willReturn(
            Arrays.asList(
                new Shop(shopResponse1.getName(), shopResponse1.getImage(), shopResponse1.getAddress(), shopResponse1.getPhoneNumber()),
                new Shop(shopResponse2.getName(), shopResponse2.getImage(), shopResponse2.getAddress(), shopResponse2.getPhoneNumber()))
        );

        //then
        final ShopResponses actual = shopService.findAll();

        assertNotNull(actual);
        assertThat(actual.getShopResponses().get(0).getName()).isEqualTo(shopResponse1.getName());
        assertThat(actual.getShopResponses().get(1).getName()).isEqualTo(shopResponse2.getName());
    }
}