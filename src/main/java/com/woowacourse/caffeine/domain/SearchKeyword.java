package com.woowacourse.caffeine.domain;

import com.woowacourse.caffeine.domain.exception.SearchKeyWordNotFoundException;

import java.util.stream.Stream;

public enum SearchKeyword {
    NAME("name", (name, pageRequest, shopRepository) ->
        shopRepository.findByNameContaining(name, pageRequest)),
    ADDRESS("address", (address, pageRequest, shopRepository) ->
        shopRepository.findByAddressContaining(address, pageRequest));

    private final String keyword;
    private final ShopSearchFunction shopSearchFunction;

    SearchKeyword(String keyword, ShopSearchFunction shopSearchFunction) {
        this.keyword = keyword;
        this.shopSearchFunction = shopSearchFunction;
    }

    public static SearchKeyword of(final String key) {
        return Stream.of(SearchKeyword.values())
            .filter(searchKeyword -> searchKeyword.keyword.equals(key))
            .findAny()
            .orElseThrow(SearchKeyWordNotFoundException::new);
    }

    public ShopSearchFunction getShopSearchFunction() {
        return shopSearchFunction;
    }
}
