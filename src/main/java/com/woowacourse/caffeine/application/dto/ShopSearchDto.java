package com.woowacourse.caffeine.application.dto;

public class ShopSearchDto {
    private String keyword;
    private String contents;

    public ShopSearchDto(final String keyword, final String contents) {
        this.keyword = keyword;
        this.contents = contents;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getContents() {
        return contents;
    }
}
