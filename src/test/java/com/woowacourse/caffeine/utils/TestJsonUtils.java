package com.woowacourse.caffeine.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public interface TestJsonUtils {

    static <T> T readJson(final String jsonPath, final Class<T> clazz) throws IOException {
        return new ObjectMapper().readValue(TestJsonUtils.class.getResourceAsStream(jsonPath), clazz);
    }
}
