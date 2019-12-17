package com.woowacourse.caffeine.dbunit;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.woowacourse.caffeine.config.DbUnitConfig;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Import(DbUnitConfig.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestExecutionListeners({
    DbUnitTestExecutionListener.class,
    DependencyInjectionTestExecutionListener.class
})
@DbUnitConfiguration(databaseConnection = "dbUnitDatabaseConnection")
@DatabaseSetup(value = "/META-INF/data.xml", type = DatabaseOperation.CLEAN_INSERT)
@DatabaseTearDown(value = "/META-INF/data.xml", type = DatabaseOperation.DELETE_ALL)
@Retention(RetentionPolicy.RUNTIME)
public @interface DbUnitTest {
}
