package com.woowacourse.caffeine.dbunit;


import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@AutoConfigureWebTestClient
@DbUnitTest
@Retention(RetentionPolicy.RUNTIME)
public @interface WebTestClientWithDbUnitTest {
}
