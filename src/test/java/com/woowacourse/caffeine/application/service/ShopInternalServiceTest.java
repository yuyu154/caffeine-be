package com.woowacourse.caffeine.application.service;

import com.woowacourse.caffeine.application.converter.ShopConverter;
import com.woowacourse.caffeine.application.dto.ShopCreateRequest;
import com.woowacourse.caffeine.application.exception.ShopNotFoundException;
import com.woowacourse.caffeine.domain.Shop;
import com.woowacourse.caffeine.mock.ShopRequestRepository;
import com.woowacourse.caffeine.mock.ShopResponseRepository;
import com.woowacourse.caffeine.repository.ShopRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ShopInternalServiceTest {

    @Mock
    private ShopRepository shopRepository;

    @InjectMocks
    private ShopInternalService shopInternalService;

    private ShopConverter shopConverter;

    @BeforeEach
    void setUp() {
        shopConverter = new ShopConverter(new ModelMapper());
    }

    @Test
    void create() {
        // given
        ShopCreateRequest shopCreateRequest = ShopRequestRepository.shopCreateRequest;
        Shop createdShop = Shop.create(shopCreateRequest);

        // when
        when(shopRepository.save(any())).thenReturn(createdShop);
        Shop shop = shopInternalService.createShop(shopCreateRequest);

        // then
        assertThat(shop.getName()).isEqualTo(shopCreateRequest.getName());
    }

    @Test
    void find_by_id() {
        // given
        long id = 1;
        Shop willFound = ShopResponseRepository.shop1;

        // when
        when(shopRepository.findById(id)).thenReturn(Optional.of(willFound));

        // then
        Optional<Shop> maybeFound = shopRepository.findById(id);
        assertThat(maybeFound).isNotEmpty();
        assertThat(maybeFound.get().getName()).isEqualTo(willFound.getName());
    }

    @Test
    void find_by_id_not_found() {
        // given
        long id = 1;

        // when
        when(shopRepository.findById(any())).thenThrow(new ShopNotFoundException(id));

        // then
        assertThrows(ShopNotFoundException.class, () -> shopInternalService.findById(id));
    }

    @Test
    void findAll() {
        //given
        List<Shop> shops = new ArrayList<>();
        Shop shop1 = ShopResponseRepository.shop1;
        Shop shop2 = ShopResponseRepository.shop2;
        shops.add(shop1);
        shops.add(shop2);

        //when
        when(shopRepository.findAll()).thenReturn(shops);

        //then
        List<Shop> actual = shopInternalService.findAll();
        assertNotNull(actual);
        assertThat(actual.get(0).getName()).isEqualTo(shop1.getName());
        assertThat(actual.get(1).getName()).isEqualTo(shop2.getName());
    }
}
