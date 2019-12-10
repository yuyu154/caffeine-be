package com.woowacourse.caffeine.util;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonUtils {

    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }
}
