package com.tanio.multitenant;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourcesConfiguration {
    @Bean(name = "customersDataSource")
    DataSource customersDataSource(CurrentTenantKey currentTenantKey) {
        DataSource targetDataSource = DataSourceBuilder.create()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .url("jdbc:mysql://localhost:3306/customers")
                .username("root")
                .password("tanio")
                .build();

        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("1", targetDataSource);

        MultiTenantDatasource multiTenantDatasource = new MultiTenantDatasource(currentTenantKey);
        multiTenantDatasource.setTargetDataSources(targetDataSources);

        return multiTenantDatasource;
    }

    @Bean(name = "inventoryDatasource")
    DataSource inventoryDataSource(CurrentTenantKey currentTenantKey) {
        DataSource targetDataSource = DataSourceBuilder.create()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .url("jdbc:mysql://localhost:3307/inventory")
                .username("root")
                .password("tanio")
                .build();

        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("1", targetDataSource);

        MultiTenantDatasource multiTenantDatasource = new MultiTenantDatasource(currentTenantKey);
        multiTenantDatasource.setTargetDataSources(targetDataSources);

        return multiTenantDatasource;
    }
}
