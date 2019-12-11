package com.woowacourse.caffeine.application.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class NotificationInternalServiceTest {

    private NotificationInternalService<Long> notificationInternalService;

    @BeforeEach
    void setUp() {
        notificationInternalService = new NotificationInternalService<>();
    }

    @Test
    void subscribe() {
        assertDoesNotThrow(() -> assertThat(notificationInternalService.subscribe(1L)).isNotNull());
    }

    @Test
    void send() {
        notificationInternalService.subscribe(1L);
        assertDoesNotThrow(() -> notificationInternalService.send(1L, "{\n" +
            "    \"topic\": \"orderArrived\",\n" +
            "    \"data\": {\n" +
            "        \"id\": 1,\n" +
            "        \"orderItems\": [\n" +
            "            {\n" +
            "                \"id\": 1,\n" +
            "                \"menuItemId\": 1,\n" +
            "                \"quantity\": 1,\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": 2,\n" +
            "                \"menuItemId\": 2,\n" +
            "                \"quantity\": 1\n" +
            "            }\n" +
            "        ]\n" +
            "    }\n" +
            "}"));
    }
}
