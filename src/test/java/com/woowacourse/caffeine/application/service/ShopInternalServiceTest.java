package com.woowacourse.caffeine.application.service;

import com.woowacourse.caffeine.application.exception.ShopNotFoundException;
import com.woowacourse.caffeine.application.dto.ShopCreateRequest;
import com.woowacourse.caffeine.application.service.ShopInternalService;
import com.woowacourse.caffeine.domain.Shop;
import com.woowacourse.caffeine.repository.ShopRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ShopInternalServiceTest {

    @Mock
    private ShopRepository shopRepository;

    @InjectMocks
    private ShopInternalService shopService;

    @Test
    void create() {
        // given
        String shopName = "별다방";
        ShopCreateRequest request = new ShopCreateRequest(shopName);
        Shop created = spy(new Shop(shopName));

        // when
        when(shopRepository.save(any())).thenReturn(created);
        Shop shop = shopService.createShop(request);

        // then
        assertThat(shop.getName()).isEqualTo(shopName);
    }

    @Test
    void find_by_id() {
        // given
        long id = 1;
        String name = "별다방";
        Shop willFound = new Shop(name);

        // when
        when(shopRepository.findById(id)).thenReturn(Optional.of(willFound));

        // then
        Optional<Shop> maybeFound = shopRepository.findById(id);
        assertThat(maybeFound).isNotEmpty();
        assertThat(maybeFound.get().getName()).isEqualTo(name);
    }

    @Test
    void find_by_id_not_found() {
        // given
        long id = 1;

        // when
        when(shopRepository.findById(any())).thenThrow(new ShopNotFoundException(id));

        // then
        assertThrows(ShopNotFoundException.class, () -> shopRepository.findById(id));
    }
}
