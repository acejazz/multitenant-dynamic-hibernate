package com.tanio.multitenant;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class MultiTenantConfiguration {
    @Bean
    public DataSource dataSource() {
        DataSource mySql1 = DataSourceBuilder.create()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .url("jdbc:mysql://localhost:3306/my_db?createDatabaseIfNotExist=true")
                .username("root")
                .password("tanio")
                .build();

        DataSource mySql2 = DataSourceBuilder.create()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .url("jdbc:mysql://localhost:3307/my_db?createDatabaseIfNotExist=true")
                .username("root")
                .password("tanio")
                .build();

        Map<Object, Object> datasources = new HashMap<>();
        datasources.put("1", mySql1);
        datasources.put("2", mySql2);

        MultiTenantDataSource multitenantDataSource = new MultiTenantDataSource();
        multitenantDataSource.setDefaultTargetDataSource(mySql1);
        multitenantDataSource.setTargetDataSources(datasources);
        multitenantDataSource.afterPropertiesSet();
        return multitenantDataSource;
    }
}