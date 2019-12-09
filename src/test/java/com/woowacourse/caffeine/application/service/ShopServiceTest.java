package com.woowacourse.caffeine.application.service;

import com.woowacourse.caffeine.application.dto.ShopResponse;
import com.woowacourse.caffeine.application.dto.ShopResponses;
import com.woowacourse.caffeine.domain.Shop;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

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


    @Test
    void findAll() {
        //given & when
        ShopResponse shopResponse1 = new ShopResponse(
            100,
            "어디야 커피 잠실점",
            "https://github.com/eunsukko/TIL/blob/master/201912/caffeine/pictures/starbucks_%EC%84%9D%EC%B4%8C%ED%98%B8%EC%88%98.jpg?raw=true",
            "서울특별시 송파구 석촌호수로 262 (송파동)",
            "02-758-8693");
        ShopResponse shopResponse2 = new ShopResponse(
            101,
            "석촌 호수",
            "https://github.com/eunsukko/TIL/blob/master/201912/caffeine/pictures/starbucks_%EC%86%A1%ED%8C%8C%EA%B5%AC%EC%B2%AD.jpeg?raw=true",
            "서울특별시 송파구 오금로 142 (송파동)",
            "02-421-3622");

        //when
        given(shopInternalService.findAll()).willReturn(
            Arrays.asList(new Shop("어디야 커피 잠실점"), new Shop("석촌 호수")));

        //then
        final ShopResponses actual = shopService.findAll();

        assertNotNull(actual);
        assertThat(actual.getShopResponses().get(0).getName()).isEqualTo(shopResponse1.getName());
        assertThat(actual.getShopResponses().get(1).getName()).isEqualTo(shopResponse2.getName());
    }
}