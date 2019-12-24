package com.woowacourse.caffeine.config;

import com.woowacourse.caffeine.application.service.NotificationInternalService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import static org.mockito.Mockito.mock;

@Configuration
public class TestDependencyConfig {

    @Bean
    @Primary
    public NotificationInternalService mockNotificationService() {
        return mock(NotificationInternalService.class);
    }
}
