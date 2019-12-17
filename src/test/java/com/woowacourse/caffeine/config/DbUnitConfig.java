package com.woowacourse.caffeine.config;

import com.github.springtestdbunit.bean.DatabaseConfigBean;
import com.github.springtestdbunit.bean.DatabaseDataSourceConnectionFactoryBean;
import org.dbunit.ext.h2.H2DataTypeFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DbUnitConfig {

    @Bean
    public DatabaseConfigBean dbUnitDatabaseConfig() {
        DatabaseConfigBean databaseConfigBean = new DatabaseConfigBean();
        databaseConfigBean.setAllowEmptyFields(true);
        databaseConfigBean.setDatatypeFactory(new H2DataTypeFactory());
        return databaseConfigBean;
    }

    @Bean
    public DatabaseDataSourceConnectionFactoryBean dbUnitDatabaseConnection(final DataSource dataSource) {
        DatabaseDataSourceConnectionFactoryBean databaseConnection = new DatabaseDataSourceConnectionFactoryBean();
        databaseConnection.setDataSource(dataSource);
        databaseConnection.setDatabaseConfig(dbUnitDatabaseConfig());
        return databaseConnection;
    }
}
